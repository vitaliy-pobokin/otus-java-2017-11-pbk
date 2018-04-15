package org.examples.pbk.otus.l151homework.frontend.encoders;

import org.examples.pbk.otus.l151homework.frontend.messages.UsersMessage;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.io.StringWriter;

public class UsersMessageEncoder implements Encoder.Text<UsersMessage> {
    @Override
    public String encode(UsersMessage usersMessage) throws EncodeException {
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
