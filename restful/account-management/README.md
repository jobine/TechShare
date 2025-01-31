# RESTful API 设计指南讲义

## 理解 RESTful 架构
开发 RESTful API 是当前开发网络应用的程序的主流方式，它有许多优势，但也并不是唯一选择。要理解 RESTful 架构，首先要理解 REST。它们之间的关系可以理解为 REST 是理论，就像建筑风格蓝图；RESTful 是实践，就像照着这个蓝图建造的房子。如果一个架构符合了 REST 的原则，那么这个架构就可以被称为是 RESTful 的。

### REST 理论
REST（**RE**presentational **S**tate **T**ransfer）是一种架构风格或设计原则，定义了在分布式系统（如 Web 服务）中设计网络应用的方法。它由 Roy Fielding 在 2000 年他的博士论文中提出[^1]，核心思想是基于 HTTP 协议去构建轻量级、可扩展的网络服务。

要理解 REST，最好的办法是是理解 Representational State Transfer 这个词组中每一个词的含义，因为它们恰恰定义了 REST 的最基本原则。

#### 资源（Resource）
REST 的中文直译是“表征状态转移”，但是这里省略了主语。这个主语就是资源（Resource）。资源是 REST 架构的核心概念，可以是网络上的任何事物或者对象，可以是具体的，也可以是抽象的，比如一篇文章、一张图片、一个用户、一个订单或者一种服务等等。每个资源都拥有唯一的标识符，即 URI（Uniform Resource Identifier，统一资源定位符）。如果你需要获取一种资源，访问它的 URI 就可以，因此URI就成了每一个资源的地址或独一无二的识别符。

#### 表征（Representation）
表征，即资源的表现形式，是我们把资源呈现出来的形式，比如一篇文章可以是 JSON 格式，也可以是 XML 格式，还可以是 HTML 格式。“表征”其实就是指资源的表现形式，用来修饰“状态转移”，即客户端和服务器端之间传递的是资源的状态表述，而不是“资源”本身。URI 仅仅代表资源的标识位置，它的具体表现形式应该在 HTTP 请求头或响应头中用 Accept 和 Content-Type 来指定，一般由 MIME 类型来确定具体的数据格式。

```http
GET /articles/1 HTTP/1.1
Accept: application/json, text/html;q=0.9, */*;q=0.8
Content-Type: application/json
```

#### 状态转移（State Transfer）
所谓“状态转移”，通俗地讲就是指客户端和服务器之间对资源的操作，导致资源状态变更的过程。状态转移描述了客户端和服务器端之间如何通过资源的表征来传递和操作资源的状态。在 REST 架构风格中，客户端通过 HTTP 请求来操作服务器端的资源，比如获取、创建、修改或删除资源。这些操作是通过 HTTP 方法来实现的，比如 GET、POST、PUT、DELETE 等。这些方法代表了对资源的操作，而资源的状态就是通过这些操作来转移的。

#### 小结
综上所述，我们总结一下什么是 REST 架构：

1. 一切皆资源，资源通过唯一的 URI 标识。 
2. 客户端通过多样的表征（如 JSON、XML、HTML）来与资源交互。 
3. 统一接口，使用标准化的 HTTP 方法（GET、POST、PUT、PATCH、DELETE等）来操作资源。

### 为什么需要 RESTful API？
RESTful API 是一种设计风格，它的优势在于简单、高效、可扩展和可靠。

RESTful API 的设计原则是基于 HTTP 协议的。因为 HTTP 协议是无状态的，所以客户端和服务器之间的操作也是无状态的，即每次请求都是独立的，服务器不会保存客户端的会话状态。这样的设计使得 RESTful API 解耦了客户端和服务器。客户端只关注资源的 URI 和表述，无需了解服务器的内部实现，这样就使得 API 的设计更加灵活和可扩展。

RESTful API 返回的数据格式通常是 JSON 或者 XML。这些格式被广泛支持，兼容各种编程语言和平台。这样就使得 RESTful API 的客户端和服务端可以使用不同的技术栈，互不影响。

RESTful API 的设计理念深刻契合现代开发的习惯。它已经成为微服务架构的重要组成部分，在分布式系统中扮演关键角色。而在前后端分离的开发模式中，RESTful API 则是前端与后端之间最主要的通信方式，为复杂应用的开发提供了有力支持。

RESTful API 拥有良好的生态支持。作为一种主流设计方式，它的工具链非常成熟，比如 Swagger 和 Postman，为开发和调试带来了极大的便利。这些特性使得 RESTful API 在实际项目中不仅好用，而且更高效，是现代开发者解决问题的重要工具。

### 为什么放弃 RESTful API？
RESTful API 有许多优点，但它并非是所有场景的唯一选择。在某些特定场景下，其局限性可能使得其他方案更加适合。

首先，当应用必须维护强状态依赖时，比如多步表单或者购物车，RESTful 的无状态性可能使开发变得复杂而繁琐。在这种情况下，采用 WebSocket、Session 或者其他协议来管理状态会更为高效。其次，对于实时性要求极高的场景，例如在线聊天或实时游戏，RESTful 的请求独立性导致无法维持长连接，而 WebSocket、gRPC 或 GraphQL 能更好地满足持续交互的需求。

同时，资源操作复杂或不直观时，RESTful 的资源模型可能难以精确映射。例如，当需要从多个资源中动态聚合数据时，GraphQL 提供了按需查询的灵活性，成为一个更优的选择。而对于轻量级系统或小规模项目，传统的服务器端渲染（如 JSP、PHP 模板）通常更为简单直接，甚至可以完全省略 API 层。

最后，在追求高性能的场景中，RESTful API 的请求结构可能包含冗余字段，导致数据传输效率低下。这时，使用 gRPC 或 Protocol Buffers 等高效的二进制协议，可以显著优化性能。

综上所述，虽然 RESTful API 是一种强大的设计模式，但根据项目需求选择适合的技术方案才是关键。理解其局限性，结合场景合理选型，才能充分发挥技术的潜力。


## RESTful API 设计原则

### 资源导向的 URI 设计

在 RESTful 架构中，每个 URI 都代表一个资源。URI 应该反应资源的层次结构，而不是操作的动作，因此 URI 应该使用名词而非动词，且通常采用复数形式。比如，`/articles` 表示文章资源，`/articles/{id}` 表示特定的文章资源。应当避免在 URI 中包含动词，比如 `/getArticles` 或 `/createArticle`，这样的 URI 设计不符合 RESTful 的原则，因为操作应当通过 HTTP 方法来表达。

```http
GET /articles       ✔️, 获取文章列表
GET /articles/1     ✔️, 获取 ID 为 1 的文章信息
GET /getArticles    ❌，GET 方法已经表示获取资源
POST /createArticle ❌，POST 方法已经表示创建资源
```

### 合理使用 HTTP 方法
对于资源的具体操作，应当使用 HTTP 方法[^2]来表达。HTTP 定义了一组请求方法，用来表示请求的目的以及请求成功后期望的结果。

#### HTTP 方法
常用的 HTTP 方法有以下几种。

- GET：检索资源表征。例如，`GET /articles` 获取文章列表，`GET /articles/{id}` 获取特定文章信息。 

  使用 GET 的请求应当仅用于获取数据，而不应包含请求内容（即请求体）。原因是 GET 请求的语义是“获取数据”，而不是“发送数据”，包含请求体会让请求的意图变得不明确。另外，GET 请求的请求体通常会被服务器、代理缓存工具等忽略，导致意外行为。最后， GET 请求会被浏览器缓存，或者被记录在日志中，存在数据泄露等安全风险。

- POST：创建新资源。例如，`POST /articles` 创建一篇新文章。

  POST 请求的请求体通常包含资源的具体内容，比如创建文章时的标题、内容等。POST 方法用于将数据提交到指定的资源，通常会导致服务器端的状态变化或副作用。

- PUT：更新资源的全部信息。例如，`PUT /articles/{id}` 更新特定文章的全部信息。

  PUT 请求的请求体通常包含资源的全部内容，用于更新指定资源的全部信息。和 POST 方法不同的是，PUT 请求是幂等的，即成功的一次请求和多次请求对服务器的预期影响是相同的，因此适合用于更新资源。

- PATCH：更新资源的部分信息。例如，`PATCH /articles/{id}` 更新特定文章的部分信息。

  PATCH 请求通常不是幂等的，这和 PUT 请求形成了鲜明对比。举例来说，如果一个资源包含了一个自动递增的计数器的字段，那么 PUT 将自然地覆盖这个字段，而 PATCH 则会增加这个字段的值。

- DELETE：删除资源。例如，`DELETE /articles/{id}` 删除特定文章。

  DELETE 请求用于删除指定的资源，通常会导致服务器端的状态变化或副作用。

此外，还有几个不太常用的 HTTP 方法。

- HEAD：获取资源的头部信息。例如，`HEAD /articles/{id}` 获取特定文章的头部信息。

  HEAD 方法和 GET 方法类似，但是不返回资源的实体内容，只返回资源的头部信息。它通常用于检查资源的元数据，比如资源的大小、类型、修改时间等。

- OPTIONS：获取资源支持的 HTTP 方法。例如，`OPTIONS /articles/{id}` 获取特定文章支持的方法。

  OPTIONS 方法用于获取资源支持的方法，通常返回资源支持的 HTTP 方法列表（响应包含 `Allow` 头，其值表明了服务器支持的所有 HTTP 方法），以及资源的元数据信息。OPTIONS 方法还可以用于 CORS 中的预检请求，以检测实际请求是否可以被服务器接受。

- TRACE：沿着到目标资源的路径执行一个消息环回测试。例如，`TRACE /articles/{id}` 沿着到目标资源的路径执行一个消息环回测试。

  TRACE 方法用于测试消息在到达服务器之前是否发生了变化，通常用于诊断和调试。TRACE 请求会在服务器端返回请求的原始内容，用于检测代理服务器或者其他中间设备对请求的修改。请求的接收者应当将收到的信息（不包括任何敏感信息字段）作为 200 OK 响应的主体返回，且 `Content-Type` 为 `message/http`。最终接收者是源服务器或者第一个在请求中收到 `Max-Forwards` 值为 `0` 的代理服务器。

- CONNECT：建立一个到目标资源的隧道。例如，`CONNECT dst.example.com:8080` 建立一个到目标资源的隧道。

  CONNECT 方法用于建立一个到目标资源的隧道，通常用于代理服务器的连接。例如，CONNECT 可以用来访问采用了 SSL（HTTPS）协议的站点，CONNECT 请求会要求 HTTP 代理服务器建立一个 TCP 连接到目标服务器，然后代理服务器会将客户端的请求转发给目标服务器，或者接收来自目标服务器的 TCP 数据流。

#### HTTP 方法的属性
HTTP 方法有一些属性，包括安全性[^3]、幂等性[^4]、缓存性等[^5]。

- 安全性（Safe）：安全性是指对资源的请求不会对资源本身产生影响。安全性是 HTTP 方法的一个重要属性，它保证了对资源的请求不会导致资源的状态变化。GET、HEAD、OPTIONS、TRACE 方法都是安全的，而 POST、PUT、DELETE 方法通常不是安全的。
- 幂等性（Idempotent）：幂等性是指对同一个资源的多次请求所产生的效果是相同的。幂等性是 HTTP 方法的一个重要属性，它保证了对同一个资源的多次请求不会产生副作用。GET、HEAD、PUT、DELETE 方法都是幂等的，而 POST、PATCH 方法通常不是幂等的。
- 缓存性（Cacheable）：缓存性是指对资源的请求是否可以被缓存。缓存性是 HTTP 方法的一个重要属性，它决定了对资源的请求是否可以被缓存。GET、HEAD、POST、PATCH、DELETE 方法都是可缓存的，而 PUT 方法通常不是可缓存的。

|方法|安全性|幂等性|缓存性|带请求体|带响应体|
|--|---|---|---|----|----|
|GET| ✔️ | ✔️ | ✔️      | ❌  | ✔️         |
|POST| ❌ | ❌ | 💡<sup>1</sup> | ✔️ | ✔️         |
|PUT| ❌ | ✔️ | ❌       | ✔️ | 💡<sup>2</sup> |
|PATCH| ❌ | ❌ | 💡<sup>1</sup> | ✔️ | 💡<sup>3</sup> |
|DELETE| ❌ | ✔️ | ❌       | ❌  | 💡<sup>4</sup> |
|HEAD| ✔️ | ✔️ | ✔️      | ❌  | ❌          |
|OPTIONS| ✔️ | ✔️ | ❌        | ❌  | ✔️         |
|TRACE| ✔️ | ✔️ | ❌       | ❌  | ✔️         |
|CONNECT| ❌ | ❌ | ❌       | ❌  | ❌          |

<small>1. 方法的缓存性取决于请求体的内容，如果请求体包含足够新的信息，那么方法是可缓存的。</small>
<small>2. 当目标资源没有当前表示，PUT 代为创建资源，必须返回 201（Created) 通知请求方资源已经创建；当资源存在当前表示，并成功进行更新，返回 200（OK）返回资源表征给请求方，或者返回 204（No Content）不返回资源表征。</small>
<small>3. 任何成功状态码（2xx 状态）都代表 PATCH 方法成功响应。200 响应可能携带响应体，204 响应不携带响应体，但需要在响应头中应加入 [ETag](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/ETag) 以方便请求者未来的[条件请求](https://developer.mozilla.org/en-US/docs/Web/HTTP/Conditional_requests)。</small>
<small>4. 如果 DELETE 方法成功执行，那么可能返回几种状态码。200（OK）表示操作已执行，并且返回资源表征；202（Accepted）表示操作可能会执行，但尚未开始执行，不返回响应体；204（No Content）表示操作已执行，不返回响应体。</small>

### 使用合适的 HTTP 状态码
HTTP 状态码是服务器对客户端请求的响应状态的标识，它包含了请求的处理结果。HTTP 状态码分为五类，分别是 1xx（信息性状态码）、2xx（成功状态码）、3xx（重定向状态码）、4xx（客户端错误状态码）和 5xx（服务器错误状态码）。在 RESTful API 中，使用合适的 HTTP 状态码是非常重要的，它可以让客户端更好地理解服务器的响应，从而更好地处理请求。

|状态码| 适用方法                  | 描述                    |
|---|-----------------------|-----------------------|
|200 OK| *                     | 请求成功，服务器返回请求资源的表征。    |
|201 Created| POST、PUT              | 请求成功，服务器创建了新资源。       |
|202 Accepted| POST、PUT、DELETE       | 请求已接受，但尚未处理。          |
|204 No Content| POST、PUT、PATCH、DELETE | 请求成功，但不返回资源表征。        |
|400 Bad Request| *                     | 请求无效，可能缺少必需参数或参数格式错误。 |
|401 Unauthorized| *                     | 请求未授权，需要提供认证信息。       |
|403 Forbidden| *                     | 已认证，没有权限访问资源。         |
|404 Not Found| *                     | 请求的资源不存在。              |
|406 Not Acceptable| POST、PUT、PATCH        | 请求的资源不支持请求的 MIME 类型。    |
|500 Internal Server Error| *                     | 服务器内部错误，无法处理请求。       |

### 域名和端点（Endpoint）的设计
我们应该尽量将 API 部署在专门的域名下，比如 `api.example.com`，这样可以更好地区分 API 和网站，方便管理和维护。如果项目规模较小，也可以将 API 部署在主域名下，比如 `example.com/api`。

端点是 API 的具体路径，它代表了资源的具体位置，端点的设计应当简洁明了，避免冗余和混淆。 
- 使用名词而非动词，通常采用复数形式。例如，`/articles` 表示文章资源。
- 使用层次结构的 URI，反映资源之间的关系。例如，`/articles/{id}/comments` 表示文章的评论资源。
- 避免层次过深，通常不超过 3 层。对于同一资源的不同表现形式，可以使用查询参数来区分，而不是使用层次结构。例如，`/articles?category=1` 表示分类为 1 的文章资源。
- 避免使用大小写，统一使用小写字母。使用连字符 `-` 分隔单词，避免使用下划线 `_`。
- 避免使用特殊字符，比如空格、斜杠、问号等，应当使用 URL 编码。

### 版本控制
RESTful API 的版本控制是为了保证 API 的稳定性和兼容性，当 API 的接口发生变化时，可以通过版本控制来保证老版本的 API 仍然可以正常使用。API 的版本控制通常有三种方式，分别是 URI 版本控制、请求头版本控制和查询参数版本控制。
- URI 版本控制：在 URI 中包含版本号，比如 `/v1/articles`。
- 请求头版本控制：在请求头中包含版本号，比如 `Accept: application/vnd.myapi.v1+json`。
- 查询参数版本控制：在查询参数中包含版本号，比如 `/articles?version=1`。

URI 版本控制和请求头版本控制是推荐的版本控制方式，它们都有各自的优缺点。URI 版本控制简单直观，但是会导致 URI 的冗余，不利于维护。请求头版本控制不会导致 URI 的冗余，但是需要客户端在请求头中指定版本号，不够直观。查询参数版本控制不推荐使用，因为查询参数通常用于过滤、排序和分页等功能，将版本号放在查询参数中会导致功能混淆。

### 安全性
安全性包括数据的保密性、完整性和可用性。为了保证 API 的安全性，可以采取以下几种措施。
- 使用 HTTPS 协议：HTTPS 是 HTTP 协议的安全版本，它通过 SSL/TLS 加密通信内容，保证了数据的保密性和完整性。RESTful API 与用户通信时，应当使用 HTTPS 协议，避免使用明文 HTTP 协议。
- 认证和授权：认证是确认用户身份的过程，授权是确认用户权限的过程。应当考虑用户的认证和授权机制，常用的身份认证方法有OAuth、JWT 等，并保证认证用户仅能访问其有权限的资源。
- 输入验证：输入验证是防止恶意攻击的重要手段。对用户输入进行验证，可以避免 SQL 注入、XSS 攻击等安全问题。
- 日志记录：日志记录是保证 API 安全性的重要手段。在设计 RESTful API 时，应当记录用户的请求和响应，保证数据的完整性和可追溯性。

### 支持过滤、排序、分页
对于返回大量数据的资源，提供过滤、排序和分页功能，以提高性能和用户体验。
- 过滤：`GET /articles?category=1` 表示分类为 1 的文章资源。
- 排序：`GET /articles?sort=created_at&order=desc` 表示按创建时间倒序排序的文章资源。
- 分页：`GET /articles?page=1&per_page=10` 表示第一页的 10 条文章资源。

### 提供一致的数据格式
为了确保 API 返回的数据格式一致，通常使用 JSON，因为它易于阅读和解析。在响应中设置正确的 Content-Type 标头，例如 application/json，并在请求中通过 Accept 标头指定可接受的响应格式。这种一致性简化了客户端的处理逻辑。

### 超媒体作为应用状态的引擎（HATEOAS, Hypermedia As The Engine Of Application State）
超媒体驱动是 REST 架构中的一个关键约束，它通过超媒体链接来驱动客户端的状态转移。超媒体链接是指在资源表征中包含了资源之间的关系，客户端通过这些链接来发现和访问资源。超媒体驱动 API 使得客户端不需要提前知道资源的 URI，而是通过资源表中的链接来动态获取资源。这种设计风格提高了 API 的灵活性和可扩展性，但也增加了客户端的复杂度，应当根据具体需求权衡是否采用 HATEOAS。

例如，当客户端请求某篇文章资源时，服务器可能返回如下 JSON 响应：
```json
{
  "id": 1,
  "title": "RESTful API 设计指南讲义",
  "links": [
    { "rel": "self", "href": "/articles/1" },
    { "rel": "comments", "href": "/articles/1/comments" }
  ]
}
```

在这个响应中，`links` 字段包含了资源之间的关系，客户端可以通过 `self` 链接获取当前文章的信息，通过 `comments` 链接获取当前文章的评论信息。

### 提供文档和自描述信息
提供详尽的 API 文档，帮助开发者理解如何使用 API。文档应包括可用的端点、请求和响应示例、错误代码说明等。此外，确保消息自描述，例如通过正确的状态码和错误消息，帮助客户端理解响应的含义。工具如 Swagger（OpenAPI）可以用于生成和维护 API 文档。

### 优化性能
关注 API 的性能，确保其响应迅速。使用缓存机制（如 HTTP 缓存头）减少不必要的请求，优化数据库查询，避免 N+1 查询问题。对于大数据量的响应，考虑使用分页或数据压缩技术。性能优化直接影响用户体验，应在设计和实现中予以重视。


## 参考文献
[^1]: Roy Thomas Fielding, "Architectural Styles and the Design of Network-based Software Architectures", DISSERTATION submitted in partial satisfaction of the requirements for the degree of DOCTOR OF PHILOSOPHY in Information and Computer Science, 2000. https://ics.uci.edu/~fielding/pubs/dissertation/top.htm
[^2]: "HTTP request methods" https://developer.mozilla.org/en-US/docs/Web/HTTP/Methods
[^3]: "Safe" https://developer.mozilla.org/en-US/docs/Glossary/Safe/HTTP
[^4]: "Idempotent" https://developer.mozilla.org/en-US/docs/Glossary/Idempotent
[^5]: "Cacheable" https://developer.mozilla.org/en-US/docs/Glossary/Cacheable
