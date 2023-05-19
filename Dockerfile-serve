FROM node:16-alpine3.12

WORKDIR /app

COPY ./package*.json ./
#RUN echo "Current working directory is: $(ls -al)"

RUN npm install -g @angular/cli
RUN npm install -g nx

RUN npm install

CMD nx serve ${APP_NAME} --host 0.0.0.0 --disable-host-check --poll 500 --verbose
#CMD nx run-many --target=serve --projects=dashboard,products --host 0.0.0.0 --disable-host-check --poll 500 --verbose
