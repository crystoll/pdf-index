package fi.solita.indexer;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

/**
 * http://vertx.io/blog/my-first-vert-x-3-application/
 */

public class SearchVerticle extends AbstractVerticle {

    @Override
    public void start(Future<Void> fut) throws Exception {
        vertx
                .createHttpServer()
                .requestHandler(r -> {
                    r.response().end("<h1>Hello from my first " +
                            "Vert.x 3 application</h1>");
                })
                .listen(8080, result -> {
                    if (result.succeeded()) {
                        fut.complete();
                    } else {
                        fut.fail(result.cause());
                    }
                });
    }
}
