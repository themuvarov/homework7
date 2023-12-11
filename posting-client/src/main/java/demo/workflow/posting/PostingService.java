package demo.workflow.posting;

import demo.model.BillingDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class PostingService implements PostingActivity {

    private static final Logger LOG = LogManager.getLogger();

    private final WebClient webClientBilling;

    public PostingService(WebClient.Builder webClientBuilder) {
        this.webClientBilling = webClientBuilder.baseUrl("http://billing-service:7878/billing/v1").build();
    }


    @Override
    public Integer billing(BillingDto billing) {
        return webClientBilling.post().uri("/bill")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(billing)
                .retrieve().onStatus(HttpStatus.NOT_ACCEPTABLE::equals,
                        response -> Mono.just(new PostingException()))
                .bodyToMono(Integer.class).block();
    }

    @Override
    public Integer rollbackBilling(BillingDto billing) {
        return webClientBilling.post().uri("/add")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(billing)
                .retrieve().onStatus(HttpStatus.NOT_ACCEPTABLE::equals,
                        response -> Mono.just(new PostingException()))
                .bodyToMono(Integer.class).block();
    }

}
