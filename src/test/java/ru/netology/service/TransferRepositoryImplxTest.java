package ru.netology.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import ru.netology.exception.InvalidCardData;
import ru.netology.model.Amount;
import ru.netology.model.CreditCard;

import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TransferRepositoryImplxTest {
    @Autowired
    private TestRestTemplate restTemplate;
    private static final GenericContainer<?> appTest = new GenericContainer<>("app:latest").withExposedPorts(5500);
    @Mock
    TransferServiceImpl service;
    @BeforeAll
    public static void setUp(){
        appTest.start();
    }
    @Test
    void doTransferTest(){
        String expected = "{\"operationId\":\"1\"}";
        final String url = "http://localhost:" + appTest.getMappedPort(5500)+"/transfer";
        CreditCard creditCard = new CreditCard("1234123412341234","12/33"
                ,"111","1234123412341233",new Amount(9000,"RUR"));
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<CreditCard>request = new HttpEntity<>(creditCard,headers);
        ResponseEntity<String>entity = restTemplate.postForEntity(url,request,String.class);
        Assertions.assertEquals(expected,entity.getBody());

    }
    @Test
    void cardNumberVerification() {
        String cardOne = "1234123412341231";
        String cardTwo = "1234123412341235";
        doThrow(InvalidCardData.class).when(service).cardNumberVerification(cardOne, cardTwo);
        Assertions.assertThrows(InvalidCardData.class, () -> service.cardNumberVerification(cardOne, cardTwo));
    }

    @Test
    void cardCvvVerification() {
        String Cvv = "11";
        doThrow(InvalidCardData.class).when(service).cardCvvVerification(Cvv);
        Assertions.assertThrows(InvalidCardData.class, () -> service.cardCvvVerification(Cvv));

    }

    @Test
    void moneyInputValid() {
        int cash = 9000;
        when(service.moneyInputValid(cash)).thenReturn(9000);
        int i = service.moneyInputValid(cash);
        Assertions.assertEquals(i,cash);
    }
}