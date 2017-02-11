# netflix-clj

A Clojure tool to preprocess the Netflix dataset to the more common
recommender format, i.e. each row is formatted as follows:

MovieID[tab]CustomerID[tab]Rating[tab]Date

## Usage

lein run -- -p PATH_TO_NETFLIX_TRAINING_FOLDER -o OUTPUT_FILE

## License

Copyright © 2017

Distributed under the Eclipse Public License either version 1.0.
