package club.tempestissimo.net.tick;

import club.tempestissimo.net.entities.Net;

import java.util.List;

public interface AbstractTickEvent {
    public abstract void tick(Net net);
}
