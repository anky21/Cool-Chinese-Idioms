/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package me.anky.coolchineseidioms.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import me.anky.coolchineseidioms.IdiomProvider;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.coolchineseidioms.anky.me",
                ownerName = "backend.coolchineseidioms.anky.me",
                packagePath = ""
        )
)
public class MyEndpoint {

    /**
     * A simple endpoint method that fetches an idiom from the Java library
     */
    @ApiMethod(name = "fetchIdiom")
    public MyBean fetchIdiom() {
        String idiom = IdiomProvider.getIdiom();
        MyBean response = new MyBean();
        response.setData(idiom);

        return response;
    }

}
