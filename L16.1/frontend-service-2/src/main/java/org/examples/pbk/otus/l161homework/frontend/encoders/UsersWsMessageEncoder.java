package org.examples.pbk.otus.l161homework.frontend.encoders;

import org.examples.pbk.otus.l161homework.frontend.wsMessages.UsersWsMessage;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.io.StringWriter;

public class UsersWsMessageEncoder implements Encoder.Text<UsersWsMessage> {
    @Override
    public String encode(UsersWsMessage usersMessage) throws EncodeException {
        StringWriter stringWriter = new StringWriter();
        try (JsonGenerator jsonGenerator = Json.createGenerator(stringWriter)) {
            jsonGenerator.writeStartObject()
                    .write("type", "users")
                    .writeStartArray("users");
                    for (String user : usersMessage.getUsers()) {
                        jsonGenerator.write(user);
                    }
            jsonGenerator.writeEnd().writeEnd();
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
