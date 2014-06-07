package com.github.altanis.arquillian.rest;

import java.lang.invoke.MethodHandles;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.altanis.arquillian.core.items.Item;
import com.github.altanis.arquillian.core.items.ItemRepository;

@Path(ItemRest.ITEM_REST_PATH)
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class ItemRest {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static final String ITEM_REST_PATH = "items";

    @Inject
    ItemRepository itemRepository;

    @GET
    @Path("/")
    public List<Item> getAllItems() {
        return itemRepository.getAll();
    }

    @GET
    @Path("{id}")
    public Response getSpecific(@PathParam("id") Long id) {
        logger.info("Getting single item with id: {}", id);
        return Response.ok().entity(itemRepository.findOne(id)).build();
    }

    @POST
    @Transactional
    public Response createNew(@NotNull @Valid Item item, @Context UriInfo uriInfo) {
        UriBuilder uriBuilder = UriBuilder.fromUri(uriInfo.getRequestUri()).path("{id}");
        Long itemId = itemRepository.save(item).getId();
        return Response.created(uriBuilder.build(uriBuilder.build(itemId))).build();
    }
}
