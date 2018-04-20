import { Message } from "./message";

export class ChatMessage extends Message {
    type: string = "chat";
    from: string;
    to: string;
    text: string;
    date: Date;
}