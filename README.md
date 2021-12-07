# corset
Cистема автоматизации прочностных расчётов стержневых систем, испытывающих растяжение-сжатие
![Screenshot 2021-12-07 at 22 59 26](https://user-images.githubusercontent.com/37376734/145097785-6e585ea6-7545-43e2-8823-7189559c9000.png)
![Screenshot 2021-12-07 at 22 59 37](https://user-images.githubusercontent.com/37376734/145097791-07f27188-80ed-45de-b6f1-9a02e2600d3d.png)
# Version
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
4. Run in terminal: `npm install -g @angular/cli`
5. Then: `npm install`
6. And: `ng serve --proxy-config proxy.conf.json`
7. `Navigate to http://localhost:4200/`
