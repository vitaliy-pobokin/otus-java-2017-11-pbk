import { Message } from './message';

export class AuthMessage extends Message {
    type: string = 'auth';
    user: string;
    success: boolean;
}
