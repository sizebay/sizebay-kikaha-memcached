# sizebay-kikaha-memcached
A simple [kikaha](http://get.kikaha.io) module to store sessions into the Memcached nodes.

## Usage
Include the `sizebay-kikaha-memcached` module into your project definition.

If you are a maven user:
```xml
<dependency>
  <artifactId>sizebay-kikaha-memcached</artifactId>
  <groupId>com.sizebay.kikaha</groupId>
  <version>0.1.0</version>
</dependency>
```

If you are using the Kikaha's command line tool:
```shell
$ kikaha project add-dep "com.sizebay.kikaha:sizebay-kikaha-memcached:0.1.0"
```

Then, you should set the `server.auth.security-context-factory` property to
`sizebay.kikaha.memcached.MemcachedSecurityContextFactory`. Bellow a full
sample configuration file configured to use a local memcached as session store.

```yml
server:
  auth:
    security-context-factory: "sizebay.kikaha.memcached.MemcachedSecurityContextFactory"

  aws:
    memcached:
      endpoint: "localhost"
      port: 11211

```

## License
This software is licenced under Apache License 2 terms.

