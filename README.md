# Korvfest-pact

## Pact broker
Starta den genom att från 'docs'-katalogen köra:

    docker-compose up docker-compose.yml

Det kommer att göra broker:n tillgänglig på localhost:9292

## Installera pact-cli-kommandon
Notera, detta är för Mac.

    brew tap pact-foundation/pact-ruby-standalone
    brew install pact-ruby-standalone

## Publicera kontrakt
Alla kommandon körs från korv-cli-katalogen.A

    ./mvnw clean verify

För att generera en ny version av kontraktet.
För att publicera till vår broker:

    pact-broker publish --broker-base-url=http://localhost:9292 target/pacts --consumer-app-version=master

## Verifiera kontrakt
För att verifiera och publicera resultat
    
    ./mvnw verify -Dpact.tag=prod -Dpact.verifier.publishResults=true -Dpact.provider.version=v1

## Tagga en kontrakt
    pact-broker create-version-tag -a korv-cli --broker-base-url http://localhost:9292  -t v1 --version master

## Can-I-Deploy
Can-i-deploy svara på frågan om mina kontrakt är verifierade.
    
    pact-broker can-i-deploy --pacticipant=korv-cli --broker-base-url=http://localhost:9292 --latest

## Pact-stub-server
Kör detta kommandon i korv-cli-katalogen
    
    pact-stub-service target/pacts -p 8080

Det är samma sak som att köra

    docker run --rm -v $(pwd)/target/pacts:/pacts -p 8080:8080 -it pactfoundation/pact-stub-server -d /pacts -p 8080

När den är uppe och snurrar kan du köra anrop mot den på 8080

    http http://localhost:8080/korvar/42
    
alt.

    curl http://localhost:8080/korvar/42

