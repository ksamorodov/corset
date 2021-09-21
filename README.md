# corset
Cистема автоматизации прочностных расчётов стержневых систем, испытывающих растяжение-сжатие
OpenJDK v16
default port: 8090

# To launch on mac os:
1. Install postgresql: `brew install postgresql`
2. Create user and database: 
  `psql postgres`
  `create database mydb;`
  `create user myuser with encrypted password 'mypass';`
  `grant all privileges on database mydb to myuser;`
3. Migration: run maven plugin with dev profile liquibase:update
