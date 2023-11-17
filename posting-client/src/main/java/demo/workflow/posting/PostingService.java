package demo.workflow.posting;

import demo.model.Billing;
import demo.model.Counter;
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

    private final WebClient webClientOutward;
    private final WebClient webClientBilling;

    public PostingService(WebClient.Builder webClientBuilder) {
        this.webClientOutward = webClientBuilder.baseUrl("http://localhost:8989/outward/v1").build();
        this.webClientBilling = webClientBuilder.baseUrl("http://localhost:7878/billing/v1").build();
    }

    @Override
    public Integer createOutward(Billing billing) {
        return webClientOutward.get().uri("/outward/" + billing.getAgent())
                .retrieve().onStatus(HttpStatus.NOT_ACCEPTABLE::equals,
                        response -> Mono.just(new PostingException()))
                .bodyToMono(Integer.class).block();
    }

    @Override
    public Integer rollbackOutward(Billing billing) {
        return webClientOutward.get().uri("/rollback/" + billing.getAgent())
                .retrieve().onStatus(HttpStatus.NOT_ACCEPTABLE::equals,
                        response -> Mono.just(new PostingException()))
                .bodyToMono(Integer.class).block();
    }

    @Override
    public Integer billing(Billing billing) {
        return webClientBilling.post().uri("/billing")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(billing)
                .retrieve().onStatus(HttpStatus.NOT_ACCEPTABLE::equals,
                        response -> Mono.just(new PostingException()))
                .bodyToMono(Integer.class).block();
    }

    @Override
    public Integer rollbackBilling(Billing billing) {
        return webClientBilling.post().uri("/rollback")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(billing)
                .retrieve().onStatus(HttpStatus.NOT_ACCEPTABLE::equals,
                        response -> Mono.just(new PostingException()))
                .bodyToMono(Integer.class).block();
    }

}
