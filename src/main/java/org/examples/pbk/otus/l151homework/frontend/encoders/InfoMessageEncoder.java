package org.examples.pbk.otus.l151homework.frontend.encoders;

import org.examples.pbk.otus.l151homework.frontend.messages.InfoMessage;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.io.StringWriter;

public class InfoMessageEncoder implements Encoder.Text<InfoMessage> {
    @Override
    public String encode(InfoMessage infoMessage) throws EncodeException {
        StringWriter stringWriter = new StringWriter();
        try (JsonGenerator jsonGenerator = Json.createGenerator(stringWriter)) {
            jsonGenerator.writeStartObject()
                    .write("type", "info")
                    .write("text", infoMessage.getText())
                .writeEnd();
        }
        return stringWriter.toString();
    }

    @Override
    public void init(EndpointConfig config) {

    }

    @Override
    public void destroy() {

    }
}
