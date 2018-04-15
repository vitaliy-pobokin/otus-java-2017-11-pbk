package org.examples.pbk.otus.l151homework.frontend.encoders;

import org.examples.pbk.otus.l151homework.frontend.messages.ChatMessage;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.io.StringWriter;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class ChatMessageEncoder implements Encoder.Text<ChatMessage> {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
                    .withLocale(Locale.US)
                    .withZone(ZoneId.systemDefault());

    @Override
    public String encode(ChatMessage chatMessage) throws EncodeException {
        StringWriter stringWriter = new StringWriter();
        try (JsonGenerator jsonGenerator = Json.createGenerator(stringWriter)){
            jsonGenerator.writeStartObject()
                    .write("type", "chat")
                    .write("from", chatMessage.getFrom())
                    .write("to", chatMessage.getTo())
                    .write("text", chatMessage.getText())
                    .write("date", FORMATTER.format(chatMessage.getDate()))
                .writeEnd();
        }
        return stringWriter.toString();
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
