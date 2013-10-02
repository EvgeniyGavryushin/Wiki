# 1. Описание

**Wiki** является программой, выполняющей поиск 1000 актеров по заданному URL-адресу конкретного актера.
Поиск происходит в рамках сайта [http://en.wikipedia.org/](http://en.wikipedia.org/)

Этот документ является инструкцией по установке и использованию.

Замеченные ошибки лучше добавлять  в [Issues] (https://github.com/EvgeniyGavryushin/Wiki/issues) проекта.

Советы, предложения, отзывы можно выслать мне на почтовый адрес <eugeny.gavryuschin@yandex.ru>.

# 2. Установка

## 2.1 Предварительные требования

* для использования из командной строки: OS Linux.

## 2.2. Установка с помощью git 

Предварительные требования:

* git - [http://git-scm.com/](http://git-scm.com/)

Установка:

* выполнить из консоли `git clone git://github.com/EvgeniyGavryushin/Wiki.git`

**Или**

* перейти по [ссылке](https://github.com/EvgeniyGavryushin/Wiki) и справа на странице нажать "Download ZIP" (download this repository as a ZIP file).

# Использование

## Как работать?

После установки заходим в папку проекта `bin`

  `cd Wiki/bin`
  
Запускаем исполняемый файл `Wiki.sh`

  `./Wiki.sh`
  
Если все было сделано верно, то вы увидите следующее:

`Enter the URL-address:`

Вам необходимо ввести URL-адресс какого-либо актера в формате `http://en.wikipedia.org/wiki/...`. Например, `http://en.wikipedia.org/wiki/Angelina_Jolie`.

Жмем `Enter`. 

Программа завершит свою работу и вы увидите:

`Done!`

В папке `bin/files` файлы `actors.txt` и `usedActors.txt` будут содержать имена 1000 найденных актеров и URL-адреса найденных актеров соответственно.

# 5. Авторы
* идея и поддержка - Сергей Крыжановский (<skryzhanovsky@ya.ru>)
* реализация - Евгений Гаврюшин (<eugeny.gavryuschin@yandex.ru>)
               
# 6. Остальное
  
Распространяется без лицензии.
