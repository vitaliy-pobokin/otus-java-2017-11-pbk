import { Message } from './message';

export class LoginMessage extends Message {
    type: string = 'login';
    username: string;
    password: string;
}