# GuestBook
Web app based on [Luminus][1] framework.

[1]: https://github.com/luminus-framework/luminus
## Prerequisites

You will need [Leiningen][2] 2.0 or above installed.

[2]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein run 

To deploy your app:

    lein run migrate
    lein uberjar
    export DATABASE_URL="jdbc:h2:~/dev/clojure/guestbook/guestbook_dev.db"
    
then run your app using:

    java -jar guestbook.jar
