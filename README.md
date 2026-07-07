# curso-udemy-microsservicos-java-com-spring-boot-e-spring-cloud
Repositório do curso de microsserviços com Spring Boot.

# Configuração do VS Code

## Supressão Avisos Spring Boot
Como este projeto utiliza uma versão mais antiga do Spring Boot para fins de aprendizado, a extensão **Spring Boot Tools** do VS Code pode exibir avisos de versão desatualizada. 

Para desativar esses avisos, crie ou configure o arquivo `.vscode/settings.json` na raiz do projeto com o seguinte conteúdo:

```json
{
    "boot-java.validation.java.version-validation": "OFF"
}
```

## Launch JSON
Use o arquivo listado em [`docs/launch.json`](docs/launch.json).