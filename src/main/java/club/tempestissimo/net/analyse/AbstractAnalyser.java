package club.tempestissimo.net.analyse;

import club.tempestissimo.awt.DataGraphWindow;
import club.tempestissimo.awt.PaintArea;
import club.tempestissimo.net.entities.Net;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface AbstractAnalyser {
    public abstract HashMap<String, List<Double>> getData();
    public abstract void analyse(Net net);

}
