package org.examples.pbk.otus.l161homework.frontend.encoders;

import org.examples.pbk.otus.l161homework.frontend.wsMessages.ChatWsMessage;

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

public class ChatWsMessageEncoder implements Encoder.Text<ChatWsMessage> {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
                    .withLocale(Locale.US)
                    .withZone(ZoneId.systemDefault());

    @Override
    public String encode(ChatWsMessage chatMessage) throws EncodeException {
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
