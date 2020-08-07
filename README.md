# A functional REST API for IMDB data

[IMDB][a1] is one of the main websites for getting information about movies, TV series and crew members such as actors and directors. Fortunately for us all, [they have an open data portal][a2], so let's benefit from it!

**Note**: This project seeks to provide a different implementation from the [original IMDB REST API project][go] (done in Golang). Although the latter is more performant, I've done serveral implementations to compare both performance and ease of use. This project seeks to:

- [x] Offer an alternative **async** REST API in Scala, that is comparable to another API done in Go.
- [x] Use a more concise syntax to obtain performance improvements and a better code readability.
- [ ] Deliver production-grade documentation and testing modules.
- [ ] Provide an interface for using simple configuration files in YAML.
- [ ] Add up a resilient system for health-checks, monitoring and logging.

## Quickstart
To use the API, first ingest the IMDB data into a MongoDB. For example, I've ingested it using PySpark into a local MongoDB instance. Then, clone this repo, install sbt (a project manager for Scala) and run `sbt run` inside this directory. Then, go to http://localhost:8080 to test the available routes.


[a1]: https://www.imdb.com/
[a2]: https://www.imdb.com/interfaces/
[go]: https://github.com/espetro/a-fast-imdb-rest-api