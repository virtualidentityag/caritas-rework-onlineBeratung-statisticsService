# Changelog

All notable changes to this project will be documented in this file. See [standard-version](https://github.com/conventional-changelog/standard-version) for commit guidelines.

## 1.12.0 (2024-06-18)


### Features

* [VIC-795] added fork auto sync config ([b283d22](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/b283d22b8199c1adb4e7ae9b88134bff3baa67c2))
* add account deletion listener ([27246a5](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/27246a5e0c3002ef6d31fa1bbff1613ce21986b2))
* add ghcr registry ([f490586](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/f4905869d4506dec8999be34ccbc73cec58d2b24))
* add number of done appointments to data export and add mongo aggregation ([cb3ec69](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/cb3ec694ba73f166a105d2cbdbec310919313a05))
* add referer to registration stats ([3ab8b9f](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/3ab8b9f1f13bdeb7f37b855d797e0bc8b9da71bb))
* add statistics endpoint and tenantId to registration statistics ([1bcbfcd](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/1bcbfcdec865da8aabd22e9c2bc2cb9d212b5c3e))
* add tenantName and agencyName to registration stats ([e801c9f](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/e801c9f494cbf99ea93df0168fb07e87a1c102d5))
* add tenantName and agencyName to registration stats ([2218bd4](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/2218bd493dbe2d1f35afe43d0cc43303c6bb1298))
* added action trigger ([a1a4229](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/a1a4229a2445e126ae17319a8a7bc57b12baa957))
* allow start-video events without sessions ([9e31b9f](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/9e31b9fe0a6589f598c57a1a9c9158866804c13f))
* booking event data enrichment and parsing ([a568da2](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/a568da2dc5c49498c3ac4e07ab5c947ffd13d113))
* caching for session request ([fa54ef3](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/fa54ef3e166a397c39d13b0db38740ec2c458f49))
* change aggregation to reflect currentBookingId ([e83e813](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/e83e813fe5c5e42447fffb594236dd3f8bc12cb2))
* create basic booking events and listeners ([1855442](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/1855442cc5914fb0e0ca30700b5ed0371e9c845b))
* disallow access to stats endpoint if stats feature is off ([9408b28](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/9408b28bf243e21c0c738041c4deb9dd56bacaca))
* disallow access to stats endpoint if stats feature is off ([2e88ac6](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/2e88ac6b625f73202282dcad4079bd7de9275b86))
* documentation ([5b5cfb7](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/5b5cfb72ed1ed6f0f1d6cbeb1e6daf3302eb1678))
* enrichment of betend if account was deleted ([d9a20a4](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/d9a20a42f9993f7598de18e61ae0eee24c5a148c))
* exclude null sessions from session counting ([768d1b7](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/768d1b73de18058d75133ad8563a600ed12ca28e))
* extend stats event message with tenantId and endData attributes ([6be9648](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/6be964885b7f3669ba452bbfefa70b6e6af16064))
* extended spring boot web debug logging ([1a5232e](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/1a5232e1d0024a32369119548c62917d6535b7bd))
* extended statistics for registration ([235ede8](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/235ede8db9e76bdf965b27d2a6b4241df64171de))
* extended statistics for registration ([e2dda36](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/e2dda36446a69c6e78fd827cce8fba3d56eaf0c1))
* first draft ([6bb6e41](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/6bb6e41ffc6a3ff7772ea4e2b1a6374451cf593c))
* implementation of stop video call listener and business logic ([e67cd2a](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/e67cd2a60e187d0bad03a876dc25e0d9c7a3498b))
* implementation of unit tests ([a206cab](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/a206cab1a2352a45bafbd0cc7c8736073bcc8a90))
* improve dev process ([3a0df81](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/3a0df813c1390e4102f001642c5edd5a70b27f11))
* improve dev processes ([88043d4](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/88043d4e43c30454a6ec40107037782bdbcc2e8c))
* insert events if session ID is given ([94d3c11](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/94d3c11f446167588e7cdd1d09959e04ebf16ce2))
* integration tests ([4ebce64](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/4ebce644b3e6afc08de401df1428223b486537a0))
* logic for keeping track of booking flows ([0b3e964](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/0b3e96415e8fa232e07a6a8e2e30a0a84ba8aa3c))
* only count appointments in the past, no matter the range ([d36eac6](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/d36eac61a1f3649b1e0c7351f88c383372734b25))
* optimization ([6036795](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/6036795e8bbedfee342554c20df095933fcd15a2))
* optimizations ([74b7df2](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/74b7df2c7fb04973eb2d3c71abeb93598356342e))
* optimizations, api endpoint for frontend, unit and integration tests ([e50e416](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/e50e4162e394937c4c255be6c5b23af07dbb27b2))
* registration event type ([6aaf062](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/6aaf062d2a882b50385a6c98bf90ce4fdec05b3c))
* registration event type and listener ([a1e5ddc](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/a1e5ddc62d142d7b3df4e07d97186e08a71618c4))
* remove extended debug logging ([1fd9eda](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/1fd9edaa2f881d960a34f3e6b98f6404fac9a8e2))
* tests ([df3678e](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/df3678e097cf688d331de7830c79ff39104d66b4))
* update of userstatisticsservice.yaml ([79a1cdf](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/79a1cdff45e67aea94a0a1e8cb51e7d79a1e3f2b))
* updated docker workflow with proper tagging ([7a87ffe](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/7a87ffef4f902b1644f6c1ac821b3791aff29fd8))
* upgrade keycloak to the latest version ([df2fb0c](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/df2fb0c4588d15de1ce52ee9e8a07ba95121c42d))
* VIC-1342 - Data Export - End date field is populated with the date when chat was Archived ([d34c1d9](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/d34c1d92d3d62b8fb6636c48bf713fbb43f0ca81))
* VIC-1342 - Data Export - End date field is populated with the date when chat was Archived ([c62960d](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/c62960d58f338bb5956d921f7d1ffbdeb3bdd406))
* VIC-1342 - Data Export - End date field is populated with the date when chat was Archived ([86a3d21](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/86a3d213d32ce4bffa8001e8c59de1266146064c))
* VIC-1342 - Data Export - End date field is populated with the date when chat was Archived ([bdee090](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/bdee090fda35b24850bd51ce0b4b09d4180ab549))
* VIC-1342 - Data Export - End date field is populated with the date when chat was Archived ([4e05397](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/4e053976e0b413461a749fd1dee54868e28f7885))
* VIC-1342 - Data Export - End date field is populated with the date when chat was Archived ([caa9a42](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/caa9a4290c430ac29dc2a0cd0b0fddcbf23e8ffa))
* VIC-1342 - Data Export - End date field is populated with the date when chat was Archived ([1e5df09](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/1e5df0904223a91bf4c00fc5d01419a7aa46b374))
* VIC-1342 - Data Export - End date field is populated with the date when chat was Archived ([6ebba86](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/6ebba86f53fd11860db0d896c4d4c89534b798cf))


### Bug Fixes

* add healthcheck ([d9f6e6c](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/d9f6e6cf1dd14da5c2dc0bc7087d5dc14dc635d8))
* add healthcheck ([966ddfd](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/966ddfd3cdbc818e9820814dad1964a6837bbae3))
* add healthcheck, removed actuator due to securityFilterChain vs KeycloakWebSecurityConfigurerAdapter incompatibility ([289839f](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/289839ffb5ba8a8ebb5e472d6ee9fa377cc0fe8b))
* add healthcheck, removed actuator due to securityFilterChain vs KeycloakWebSecurityConfigurerAdapter incompatibility ([e1af520](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/e1af52080aaf46591e5a7ff4a36ff9d71876c8f4))
* add healthcheck, removed actuator due to securityFilterChain vs KeycloakWebSecurityConfigurerAdapter incompatibility ([c805275](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/c8052756e219e4a3c8559f68e8dbd5b8ebac8342))
* add healthcheck, removed actuator due to securityFilterChain vs KeycloakWebSecurityConfigurerAdapter incompatibility ([8b7767c](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/8b7767c099500024bf07a63a9033a66933819456))
* add healthcheck, removed actuator due to securityFilterChain vs KeycloakWebSecurityConfigurerAdapter incompatibility ([4d9ef1d](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/4d9ef1d2b82c08a85e568856aa6819e9339fe890))
* add healthcheck, removed actuator due to securityFilterChain vs KeycloakWebSecurityConfigurerAdapter incompatibility ([c8dd6ef](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/c8dd6ef78dfa378b1238569e4326e853d741dad3))
* aggregation parameter index ([8eafcc3](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/8eafcc381390b8f5e7c844f73f13394605bc32b3))
* allow null session IDs ([5efaf01](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/5efaf011f6656f42c08a754cb90c828da8d9be1b))
* api response handler has returned html response ([3a66934](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/3a669344f2b0c79edfee62e072f3c043ad2b4bdb))
* betend should be set for session delete testcase ([c9c7dfb](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/c9c7dfbef1fcf75022b6a1c1ca985bf5dee309d3))
* class cast exception for statistics ([69757f8](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/69757f8020b42feea90a68fbe76673fce97b95f7))
* class cast exception for statistics ([784dc57](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/784dc57880c3502e81e4ec92dca481b6f0d5dca8))
* class cast exception for statistics ([d8ee537](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/d8ee53788b6243bf8c59ca6eef8b6df830761cd3))
* code smells ([8a0c7b7](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/8a0c7b79145081f1ce7b5ac86cca53da0d380702))
* code smells ([709e9b8](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/709e9b8852d6a5bb27150638079c07ac2bc37725))
* disable embedded mongo autocongiguration for healtchecks ([0216372](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/02163723261db11a6b96b19d6b580f46aae2ccc6))
* docu ([37e22da](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/37e22da49edb1bbcd5a0a65b6c19d265e936066b))
* doubled start-video-call find when same call id ([dfb5863](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/dfb5863c6d2e1cdc4d03973729e83b8944265538))
* doubled start-video-call status find ([341ceb0](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/341ceb0310fca04f3ba007567d71d6f4a38d81f0))
* fix log information ([a7d5424](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/a7d54240f3687953be5142611624e9caf92a05ba))
* fix object comparision bug for statistics ([8d9ffaf](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/8d9ffafcae442a0f56b798954cdcf13c2f9d97b6))
* fix proper exception handling ([18f4aa9](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/18f4aa925e8d4f74a3289e6a962feb27117021c0))
* improve and fix CVEs ([9ecda66](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/9ecda6638373037010efb8e1f841a8d76a2fc1c0))
* improve and fix CVEs ([434ef14](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/434ef1416c3a2eac9456e1d5028001b77f6ca68b))
* improve and fix CVEs ([62094b6](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/62094b650103941d2fd635da69515c9ac612c5e0))
* improve and fix CVEs ([eb424ef](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/eb424ef9e0f7717c688ce351aa6894971427504a))
* indention format ([0b5abe5](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/0b5abe5d36c064d4f67317caf9960ebe7050f9dc))
* introduce trivy image scan ([68d3e87](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/68d3e87c94e453ffffbf034770a51d4f683ce91e))
* introduce trivy image scan ([c904b78](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/c904b78dc743f50b910cee4e7655ed3ba6fb65f1))
* introduce trivy image scan ([f9f72fc](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/f9f72fc29cf3d89dd090029fe09c3e9603671cc6))
* null check convention ([195b626](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/195b62644e60c4f75958c8fc245c30778081a5a8))
* prevent NPE for mongo aggregation queries ([b27290f](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/b27290ff7d5091cd76d9213c3b3845d9b0086419))
* spring boot starter ([20111a8](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/20111a8afdaf1838c0ff6e58bc8b39f6b8b48aba))
* stateless api client for call to userservice ([e1479d4](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/e1479d4c2bbb26c6d70d7a964d0ca8602ceb2e25))
* store tenant id in metadata to enable filtering by tenant id ([7f66acb](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/7f66acbb5a15df66e602ce99df48fdad3df9794c))
* store tenant id in metadata to enable filtering by tenant id ([96382f0](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/96382f088265eed88fdb535486b424b9e6a632e3))
* styling ([f4ed0c3](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/f4ed0c3d351aecbb67ac16cc9a59ecab69d08d07))
* tenant resolution for SaaS ([46c9829](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/46c9829b627d59c25c409c6cb5443333ed7800b7))
* tenant setting retrieval for superadmin ([356bc8b](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/356bc8b7f0cf149778707fb763902b7c9e73c10d))
* unit test ([272a85b](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/272a85b6e9583f67e45e03b6e96206085a15cefd))
* update log4j to safe version ([440e4dd](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/440e4dd6d9039baaf5e043b041ea54f3659ce492))

## [1.11.0](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/compare/v1.10.0...v1.11.0) (2022-04-15)


### Features

* upgrade keycloak to the latest version ([df2fb0c](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/df2fb0c4588d15de1ce52ee9e8a07ba95121c42d))


### Bug Fixes

* update log4j to safe version ([440e4dd](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/440e4dd6d9039baaf5e043b041ea54f3659ce492))

## 1.10.0 (2021-12-14)


### Features

* caching for session request ([fa54ef3](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/fa54ef3e166a397c39d13b0db38740ec2c458f49))
* documentation ([5b5cfb7](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/5b5cfb72ed1ed6f0f1d6cbeb1e6daf3302eb1678))
* first draft ([6bb6e41](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/6bb6e41ffc6a3ff7772ea4e2b1a6374451cf593c))
* implementation of stop video call listener and business logic ([e67cd2a](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/e67cd2a60e187d0bad03a876dc25e0d9c7a3498b))
* implementation of unit tests ([a206cab](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/a206cab1a2352a45bafbd0cc7c8736073bcc8a90))
* integration tests ([4ebce64](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/4ebce644b3e6afc08de401df1428223b486537a0))
* optimization ([6036795](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/6036795e8bbedfee342554c20df095933fcd15a2))
* optimizations ([74b7df2](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/74b7df2c7fb04973eb2d3c71abeb93598356342e))
* optimizations, api endpoint for frontend, unit and integration tests ([e50e416](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/e50e4162e394937c4c255be6c5b23af07dbb27b2))
* tests ([df3678e](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/df3678e097cf688d331de7830c79ff39104d66b4))
* update of userstatisticsservice.yaml ([79a1cdf](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/79a1cdff45e67aea94a0a1e8cb51e7d79a1e3f2b))


### Bug Fixes

* api response handler has returned html response ([3a66934](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/3a669344f2b0c79edfee62e072f3c043ad2b4bdb))
* prevent NPE for mongo aggregation queries ([b27290f](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/b27290ff7d5091cd76d9213c3b3845d9b0086419))
* unit test ([272a85b](https://github.com/CaritasDeutschland/caritas-onlineBeratung-statisticsService/commit/272a85b6e9583f67e45e03b6e96206085a15cefd))
