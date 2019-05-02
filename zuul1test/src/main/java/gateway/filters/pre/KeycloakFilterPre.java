package gateway.filters.pre;

import com.netflix.zuul.*;
import com.netflix.zuul.context.*;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.representation.TokenIntrospectionResponse;
import org.keycloak.representations.idm.authorization.AuthorizationRequest;
import org.keycloak.representations.idm.authorization.AuthorizationResponse;
import org.keycloak.representations.idm.authorization.Permission;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;

import javax.servlet.http.*;

@Component
public class KeycloakFilterPre extends ZuulFilter {
    private static final Logger logger = LoggerFactory.getLogger(KeycloakFilterPre.class);
    private static final String AUTHORIZATION_HEADER = "authorization";
    private static String accessToken;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 2;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest req = context.getRequest();
        logger.debug("*** RouteFilter(): Processing incoming request for {}, {}, {}.", req.getRequestURI(), req.getRequestURL().toString(), req.getRemoteAddr());

// create a new instance based on the configuration defined in keycloak-authz.json
        AuthzClient authzClient = AuthzClient.create();

// create an authorization request
        AuthorizationRequest request = new AuthorizationRequest();

// send the entitlement request to the server in order to
// obtain an RPT with all permissions granted to the user
//        AuthorizationResponse response = authzClient.authorization("alice", "alice").authorize(request);
//        String rpt = response.getToken();
//
//        System.out.println("You got an RPT: " + rpt);
//        // introspect the token
//        TokenIntrospectionResponse requestingPartyToken = authzClient.protection().introspectRequestingPartyToken(rpt);
//
//        System.out.println("Token status is: " + requestingPartyToken.getActive());
//        System.out.println("Permissions granted by the server: ");
//
//        for (Permission granted : requestingPartyToken.getPermissions()) {
//            System.out.println(granted);
//        }

        return null;
    }

    /**
     * Obtain an Entitlement API Token or EAT from the server. Usually, EATs are going to be obtained by clients using a
     * authorization_code grant type. Here we are using resource owner credentials for demonstration purposes.
     *
     * @param authzClient the authorization client instance
     * @return a string representing a EAT
     */
    private static String getEntitlementAPIToken(AuthzClient authzClient) {
        return authzClient.obtainAccessToken("alice", "alice").getToken();
    }
}