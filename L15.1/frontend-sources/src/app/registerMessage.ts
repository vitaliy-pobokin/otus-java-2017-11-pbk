import { Message } from './message';

export class RegisterMessage extends Message {
    type: string = 'register';
    username: string;
    password: string;
}