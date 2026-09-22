# 📞 Guia Passo a Passo — Código Exato (Copiar e Colar)

Cada passo mostra:
1. ❌ **O que está lá agora** (original)
2. ✅ **O que você cola** (código novo)
3. 📄 **Como o arquivo inteiro fica no final**

---

## 🔵 PASSO 1 — Criar dois arquivos NOVOS

Esses arquivos **não existem ainda**. Você vai criar do zero na mesma pasta dos outros `.java`.

---

### 📄 Arquivo NOVO: `Observer.java`

Crie o arquivo `Observer.java` e cole **tudo** isso dentro:

```java
/**
 * Interface Observer do padrão Observer.
 * Garante o baixo acoplamento: qualquer classe que implemente
 * esta interface pode "escutar" mudanças no Subject (PhoneModel)
 * sem precisar conhecer seus detalhes internos.
 */
public interface Observer {

    /**
     * Chamado pelo Subject quando houver uma mudança (novo dígito adicionado).
     * @param digit o dígito que acabou de ser adicionado
     */
    void update(int digit);
}
```

---

### 📄 Arquivo NOVO: `Subject.java`

Crie o arquivo `Subject.java` e cole **tudo** isso dentro:

```java
/**
 * Interface Subject do padrão Observer.
 * O Subject é quem detém os dados e avisa os observadores
 * registrados quando há uma mudança de estado.
 */
public interface Subject {

    /**
     * Adiciona um observador à lista de notificação.
     */
    void registerObserver(Observer o);

    /**
     * Remove um observador da lista de notificação.
     */
    void removeObserver(Observer o);

    /**
     * Percorre todos os observadores registrados chamando update().
     * @param digit o dígito que gerou a notificação
     */
    void notifyObservers(int digit);
}
```

> [!IMPORTANT]
> **Commit 1** — `git add Observer.java Subject.java && git commit -m "feat: cria as interfaces Subject e Observer"`

---

## 🟢 PASSO 2 — Modificar `PhoneModel.java`

### ❌ ANTES — Arquivo original completo:

```java
import java.util.ArrayList;
import java.util.List;

/**
 * Store a phone number, digit-by-digit
 */
public class PhoneModel {
    private List<Integer> digits = new ArrayList<>();

    public void addDigit(int newDigit) {
        digits.add(newDigit);
    }

    public List<Integer> getDigits() {
        return digits;
    }
}
```

### 🔍 Onde mexer (3 lugares):

**Lugar 1 — Linha 7:** A declaração da classe

| Antes | Depois |
|---|---|
| `public class PhoneModel {` | `public class PhoneModel implements Subject {` |

**Lugar 2 — Linha 9:** Logo abaixo de `private List<Integer> digits...`, adicionar novo atributo

| Antes | Depois |
|---|---|
| *(nada, só a linha do digits)* | Adicionar nova linha: `private List<Observer> observers = new ArrayList<>();` |

**Lugar 3 — Linha 11-12:** Dentro do método `addDigit`, após `digits.add(newDigit);`

| Antes | Depois |
|---|---|
| Só tem `digits.add(newDigit);` | Adicionar abaixo: `notifyObservers(newDigit);` |

**Lugar 4 — Após o método `getDigits()`:** Adicionar 3 métodos novos no final da classe

---

### ✅ DEPOIS — Apague TUDO do `PhoneModel.java` e cole isso:

```java
import java.util.ArrayList;
import java.util.List;

/**
 * Store a phone number, digit-by-digit.
 * Agora implementa Subject do padrão Observer: é a fonte da verdade
 * que notifica os observadores quando um novo dígito é adicionado.
 */
public class PhoneModel implements Subject {
    private List<Integer> digits = new ArrayList<>();

    // Lista de observadores registrados que serão notificados a cada novo dígito
    private List<Observer> observers = new ArrayList<>();

    public void addDigit(int newDigit) {
        digits.add(newDigit);

        // Notifica todos os observadores imediatamente após adicionar o dígito.
        // Isso garante que a UI (Screen) seja atualizada em tempo real,
        // sem que o PhoneModel precise conhecer a Screen diretamente.
        notifyObservers(newDigit);
    }

    public List<Integer> getDigits() {
        return digits;
    }

    // --- Métodos do padrão Observer (implementação da interface Subject) ---

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers(int digit) {
        // Percorre todos os observadores e chama update() em cada um,
        // entregando o dígito que acabou de ser adicionado
        for (Observer observer : observers) {
            observer.update(digit);
        }
    }
}
```

> [!IMPORTANT]
> **Commit 2** — `git add PhoneModel.java && git commit -m "refactor: transforma PhoneModel no Subject do padrao Observer"`

---

## 🟡 PASSO 3 — Modificar `Screen.java` (Observer 1)

### ❌ ANTES — Arquivo original completo:

```java
/**
 * Prints things out to the screen, when needed
 * Printing to the screen:
 *  System.out.println("hello");
 */
public class Screen {
    private final PhoneModel model;

    public Screen(PhoneModel model) {
        this.model = model;
    }
}
```

### 🔍 Onde mexer:

**Lugar único — Linha 10:** Dentro do construtor, **logo depois** de `this.model = model;`

Você vai adicionar a criação e registro do Observer 1.

---

### ✅ DEPOIS — Apague TUDO do `Screen.java` e cole isso:

```java
/**
 * Prints things out to the screen, when needed.
 * A Screen não é chamada diretamente por ninguém — ela se registra
 * como observadora do PhoneModel e reage automaticamente às mudanças.
 */
public class Screen {
    private final PhoneModel model;

    public Screen(PhoneModel model) {
        this.model = model;

        // --- Observador 1: imprime cada dígito recebido ---
        // Cria uma classe anônima que implementa Observer.
        // Toda vez que um dígito é adicionado no PhoneModel,
        // o método update() será chamado e imprime o dígito na tela.
        Observer digitPrinter = new Observer() {
            @Override
            public void update(int digit) {
                System.out.println(digit);
            }
        };
        model.registerObserver(digitPrinter);
    }
}
```

### 🖥️ Teste agora! Rode o `Main.java`. A saída deve ser:

```
Pressing: 5
5
Pressing: 3
3
Pressing: 7
7
... (12 vezes)
```

Cada número sozinho abaixo do "Pressing:" vem do seu Observer 1.

> [!IMPORTANT]
> **Commit 3** — `git add Screen.java && git commit -m "feat: adiciona primeiro observador na Screen para imprimir digito recente"`

---

## 🔴 PASSO 4 — Modificar `Screen.java` (Observer 2)

### ❌ ANTES — O que está lá agora (resultado do Passo 3):

```java
        model.registerObserver(digitPrinter);
    }
}
```

### 🔍 Onde mexer:

**Lugar único:** Logo **depois** da linha `model.registerObserver(digitPrinter);` e **antes** do `}` que fecha o construtor.

---

### ✅ DEPOIS — Apague TUDO do `Screen.java` e cole a versão FINAL:

```java
/**
 * Prints things out to the screen, when needed.
 * A Screen não é chamada diretamente por ninguém — ela se registra
 * como observadora do PhoneModel e reage automaticamente às mudanças.
 *
 * Usa o padrão Observer com dois observadores:
 *   1) digitPrinter: imprime cada dígito assim que chega
 *   2) dialChecker: verifica se o número está completo (12 dígitos) e simula a discagem
 */
public class Screen {
    private final PhoneModel model;

    public Screen(PhoneModel model) {
        this.model = model;

        // --- Observador 1: imprime cada dígito recebido ---
        // Cria uma classe anônima que implementa Observer.
        // Toda vez que um dígito é adicionado no PhoneModel,
        // o método update() será chamado e imprime o dígito na tela.
        // A IA sugeriu usar classe anônima para manter cada observer isolado.
        Observer digitPrinter = new Observer() {
            @Override
            public void update(int digit) {
                System.out.println(digit);
            }
        };
        model.registerObserver(digitPrinter);

        // --- Observador 2: verifica se completou a discagem ---
        // Quando o número de dígitos chega a 12, imprime a mensagem
        // "Agora discando ..." com todos os dígitos concatenados.
        // Ele consulta model.getDigits() para saber a quantidade atual.
        Observer dialChecker = new Observer() {
            @Override
            public void update(int digit) {
                // Verifica se o número está completo (12 dígitos)
                if (model.getDigits().size() == 12) {
                    System.out.print("Agora discando ");
                    // Percorre todos os dígitos armazenados e imprime na mesma linha
                    for (Integer d : model.getDigits()) {
                        System.out.print(d);
                    }
                    System.out.println("...");
                }
            }
        };
        model.registerObserver(dialChecker);
    }
}
```

> [!IMPORTANT]
> **Commit 4** — `git add Screen.java && git commit -m "feat: adiciona segundo observador na Screen para simular discagem final"`

---

## 🖥️ Saída Final Esperada (Rodando `Main.java`)

```
Pressing: 5
5
Pressing: 3
3
Pressing: 7
7
Pressing: 1
1
Pressing: 9
9
Pressing: 0
0
Pressing: 4
4
Pressing: 8
8
Pressing: 2
2
Pressing: 6
6
Pressing: 11
11
Pressing: 10
10
Agora discando 53719048261110...
```

> [!NOTE]
> Os números são aleatórios — cada execução gera dígitos diferentes. O que importa é o formato:
> - Cada dígito aparece logo abaixo do seu "Pressing:"
> - "Agora discando ..." aparece **só uma vez**, no final, com os 12 dígitos concatenados

---

## 📂 Estrutura Final do Projeto

```
telephone/
├── KeyPad.java        ← NÃO MEXE (original)
├── Main.java          ← NÃO MEXE (original)
├── Observer.java      ← NOVO   (Passo 1)
├── Subject.java       ← NOVO   (Passo 1)
├── PhoneModel.java    ← MODIFICADO (Passo 2)
└── Screen.java        ← MODIFICADO (Passo 3 + 4)
```

---

## 📋 Resumo dos Commits

| Commit | Comando Git | Arquivos |
|---|---|---|
| 1 | `git commit -m "feat: cria as interfaces Subject e Observer"` | `Observer.java`, `Subject.java` |
| 2 | `git commit -m "refactor: transforma PhoneModel no Subject do padrao Observer"` | `PhoneModel.java` |
| 3 | `git commit -m "feat: adiciona primeiro observador na Screen para imprimir digito recente"` | `Screen.java` |
| 4 | `git commit -m "feat: adiciona segundo observador na Screen para simular discagem final"` | `Screen.java` |
