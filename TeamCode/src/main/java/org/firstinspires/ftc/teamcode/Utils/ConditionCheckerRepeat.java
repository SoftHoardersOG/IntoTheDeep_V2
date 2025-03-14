package org.firstinspires.ftc.teamcode.Utils;

public class ConditionCheckerRepeat extends Thread{
    private LambdaBool _lambdaC;
    private Lambda _lambdaA;
    private int stepTime;
    private int steps;

    public static int timeToEnd = 30000;
    public static boolean opModeStopped = false;

    public ConditionCheckerRepeat(LambdaBool lambdaCondition, Lambda lambdaAction, int _stepTime){
        _lambdaC = lambdaCondition;
        _lambdaA = lambdaAction;
        stepTime = _stepTime;
        steps = 0;
    }

    @Override
    public void run() {
        long time = System.currentTimeMillis();
        while (!_lambdaC.run() && !opModeStopped){
            if (System.currentTimeMillis()-time > timeToEnd || opModeStopped){
                return;
            }
            if ((System.currentTimeMillis()-time) / stepTime > steps){
                steps++;
                _lambdaA.run();
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (opModeStopped) return;
    }
}
