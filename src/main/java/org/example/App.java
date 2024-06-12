package org.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.templ.freemarker.FreeMarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import org.example.model.Product;

public class App extends AbstractVerticle {

  private List<Product> products = new ArrayList<>();
  public static FreeMarkerTemplateEngine templateEngine;

  @Override
  public void start() {
    templateEngine = FreeMarkerTemplateEngine.create(vertx);

    Router router = Router.router(vertx);

    router.route().handler(BodyHandler.create());

    router.get("/products").handler(this::handleGetProducts);
    router.post("/products").handler(this::handleAddProduct);

    router.route("/*").handler(StaticHandler.create("src/main/resources"));

    vertx.createHttpServer().requestHandler(router).listen(9090, server -> {

      if (server.succeeded()) {
        System.out.println(
            "Server running on port " + server.result().actualPort() + "..............");
      } else {
        System.out.println("Server failed to listen on port " + server.result().actualPort());
      }

    });


  }

  private void handleGetProducts(RoutingContext context) {
    context.put("products", products);
    templateEngine.render(context.data(), "products.ftl", res -> {
      if (res.succeeded()) {
        context.response().putHeader("content-type", "text/html").end(res.result());
      } else {
        context.fail(res.cause());
      }
    });
  }

  // RequestContext is the interface that have req, res
  private void handleAddProduct(RoutingContext context) {

    System.out.println(context.body().asString());
    String name = context.request().getParam("name");
    double price = Double.parseDouble(context.request().getParam("price"));

    int id = products.size() + 1;
    Product product = new Product(id, name, price);

    products.add(product);
    context.response().setStatusCode(303).putHeader("Location", "/products")
        .putHeader("content-type", "text/html").end();

  }

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new App());
  }
}
