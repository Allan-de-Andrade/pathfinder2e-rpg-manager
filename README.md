# 🎲 Pathfinder 2e RPG Manager

> ⚠️ **Projeto em desenvolvimento** — funcionalidades ainda sendo construídas. Contribuições e sugestões são bem-vindas!

Gerenciador de fichas e combates para o RPG de mesa **Pathfinder 2e**. O objetivo é digitalizar e automatizar as principais mecânicas do sistema, facilitando a vida de jogadores e mestres durante as sessões.

---

## 🧩 Sobre o Projeto

O Pathfinder 2e possui um sistema de regras denso e detalhado. Este projeto nasceu da necessidade de ter uma ferramenta prática que centralize o gerenciamento de personagens e o fluxo de combate, sem depender de soluções genéricas que não refletem bem as particularidades do sistema.

---

## 🛠️ Tecnologias

| Camada | Tecnologia |
|---|---|
| Back-End | Java · Spring Boot |
| Front-End | Angular |
| Banco de Dados | MySQL |
| Infraestrutura | Docker · Docker Compose |

---

## 📁 Estrutura do Repositório

```
pathfinder2e-rpg-manager/
├── backend/
│   └── rpg-manager/       # API REST em Spring Boot
├── frontend/
│   └── rpg-manager/       # Interface em Angular
├── docker-compose.yml     # Orquestração dos serviços
└── README.md
```

---

## 🚀 Como Rodar

### Pré-requisitos

- [Docker](https://www.docker.com/) e Docker Compose instalados

### Subindo o ambiente

```bash
git clone https://github.com/Allan-de-Andrade/pathfinder2e-rpg-manager.git
cd pathfinder2e-rpg-manager
docker-compose up
```

> ℹ️ As instruções de configuração serão expandidas conforme o projeto evoluir.

---

## 📌 Status

🔨 **Em construção** — este repositório está nos estágios iniciais. As funcionalidades planejadas incluem:

- [ ] Sistema de Usuarios com Autenticação OAuth e Token JWT
- [ ] Criação e edição de fichas de personagem
- [ ] Motor de combate com controle de turnos e ações
- [ ] Gerenciamento de condições e efeitos
- [ ] Suporte a múltiplos jogadores por sessão

---

## 👤 Autor

Feito por [Allan Victor](https://github.com/Allan-de-Andrade)  
📧 allanvictorsilvaandrade@gmail.com
