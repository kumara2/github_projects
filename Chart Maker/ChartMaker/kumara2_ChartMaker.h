#include<iostream>
#include<cstdlib>
#include<cmath>
#include"kumara2_Chart.h"

class ChartMaker{

 private:
  Chart c, c2, c3;

 public:
    int run();
    void printPoints(Chart c);
    void quit();
    void addPoints();
    void subPoints();
    void load();
    void load2();
    void help();
    void increaseScale();
    void decreaseScale();
    void printHistogram();
    void shiftLeft();
    void shiftRight();
    void save();
};
