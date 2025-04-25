

# 🏁 F1 API - Gerenciamento de Pilotos e Times de Fórmula 1

Aplicação **RESTful** desenvolvida com **Spring Boot**, voltada para o gerenciamento de **Pilotos de Fórmula 1** e seus respectivos **Times**. Ideal para estudos e projetos acadêmicos que envolvem persistência de dados com **MariaDB**, **JPA**, e boas práticas de desenvolvimento em Java.

---

## 🎯 Objetivo

> Construir uma API capaz de:
- ✅ Cadastrar, consultar, atualizar e deletar **Pilotos**
- ✅ Cadastrar, consultar, atualizar e deletar **Times**
- ✅ Gerenciar o relacionamento entre **Pilotos** e **Times** (vários pilotos por time)

---

## 🧰 Tecnologias Utilizadas

- ☕ Java + Spring Boot  
- 🌐 Spring Web  
- 🗃️ Spring Data JPA  
- 🛠️ Lombok  
- 🐬 MariaDB  
- 🔄 DevTools  
- 📬 Postman ou Bruno (para testar os endpoints)

---

## ⚙️ Configuração do Ambiente

### 1️⃣ Banco de Dados (MariaDB)

Instale e configure o **XAMPP** com **MariaDB**.  
Crie o banco com o seguinte comando:

```sql
CREATE DATABASE f1db;
```

### 2️⃣ Arquivo `application.properties`

Configure as credenciais de conexão em:

`src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mariadb://localhost:3306/f1db
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## 🧩 Estrutura das Entidades

### 🏎️ Pilot.java

```java
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "team")
public class Pilot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String firstName;
    private String lastName;
    private int racingNumber;
    private int wins;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;
}
```

### 🏁 Team.java

```java
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "pilots")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String headquarters;
    private int foundedYear;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Pilot> pilots;

    public void addPilot(Pilot pilot) {
        this.pilots.add(pilot);
        pilot.setTeam(this);
    }
}
```

---

## 📦 Repositórios

Interfaces que estendem `JpaRepository` para facilitar operações CRUD:

```java
public interface PilotRepository extends JpaRepository<Pilot, Long> {}
public interface TeamRepository extends JpaRepository<Team, Long> {}
```

---

## 🌐 Endpoints REST

### 🚗 PilotController

```java
@RestController
@RequestMapping("/api/pilots")
public class PilotController {

    @Autowired
    private PilotRepository pilotRepository;

    @GetMapping
    public List<Pilot> list() {
        return pilotRepository.findAll();
    }

    @PostMapping
    public Pilot create(@RequestBody Pilot pilot) {
        return pilotRepository.save(pilot);
    }

    // Métodos para update, delete etc.
}
```

### 🏢 TeamController

```java
@RestController
@RequestMapping("/api/teams")
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;

    @GetMapping
    public List<Team> list() {
        return teamRepository.findAll();
    }

    @PostMapping
    public Team create(@RequestBody Team team) {
        return teamRepository.save(team);
    }

    // Métodos para update, delete etc.
}
```

---

## 🧪 Testando com Postman/Bruno

Use as seguintes rotas para testar a API:

| Método | Endpoint              | Descrição                          |
|--------|-----------------------|------------------------------------|
| GET    | `/api/pilots`         | Lista todos os pilotos             |
| POST   | `/api/pilots`         | Cadastra um novo piloto            |
| GET    | `/api/teams`          | Lista todos os times               |
| POST   | `/api/teams`          | Cadastra um novo time              |

📝 Para associar um piloto a um time, envie o `id` do time no JSON de criação do piloto.

---

## ✅ Funcionalidades

- 🔗 Relacionamento **Many-to-One** entre Pilotos e Times  
- 🔍 Operações CRUD com Spring Data  
- 🧼 Redução de boilerplate com Lombok  
- 💾 Persistência em banco relacional (MariaDB)  
- 🔁 Evita loops de serialização com `@ToString(exclude = ...)`  
- ➕ Método utilitário `addPilot(Pilot)` em `Team`

---

## 🚀 Como Rodar o Projeto

1. Clone o repositório:

```bash
git clone https://github.com/seu-usuario/pilotos-f1-api.git
cd pilotos-f1-api
```

2. Configure o banco de dados como descrito acima.

3. Execute a aplicação:

```bash
./mvnw spring-boot:run
```

4. Acesse a API via Postman ou Bruno:
```
http://localhost:8080/api/pilots
http://localhost:8080/api/teams
```

---

## 📄 Licença

Este projeto é de **uso acadêmico**, desenvolvido com fins didáticos para a disciplina de persistência com Spring Boot.

---
