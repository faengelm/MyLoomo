package com.gmail.faengelm.myloomo;




/**
 * Created by mss on 24.01.18.
 */

public class EmojiCommand extends MessageCommand {

    public EmojiCommand(String[] message) {
        super(message);
    }

    @Override
    public void execute() {
        LoomoEmojiService.getInstance().doEmoji(message[1]);
    }
}