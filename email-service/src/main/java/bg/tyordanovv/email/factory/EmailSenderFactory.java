package bg.tyordanovv.email.factory;

import bg.tyordanovv.core.email.EmailType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EmailSenderFactory {
    private static Map<EmailType, Sender> viewerMap;

    @Autowired
    private EmailSenderFactory(List<Sender> senders) {
        viewerMap = senders.stream().collect(Collectors.toUnmodifiableMap(Sender::getType, Function.identity()));
    }

    public static <T> Sender<T> getSender(EmailType senderType) {
        return   Optional.ofNullable(viewerMap.get(senderType)).orElseThrow(IllegalArgumentException::new);
    }
}
