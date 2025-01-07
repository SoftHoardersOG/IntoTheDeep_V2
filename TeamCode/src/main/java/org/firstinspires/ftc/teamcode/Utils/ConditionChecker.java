package org.firstinspires.ftc.teamcode.Utils;

public class ConditionChecker extends Thread{

    private LambdaBool _lambdaC;
    private Lambda _lambdaA;

    public static int timeToEnd = 30000;

    public ConditionChecker(LambdaBool lambdaCondition, Lambda lambdaAction){
        _lambdaC = lambdaCondition;
        _lambdaA = lambdaAction;
    }

    @Override
    public void run() {
        long time = System.currentTimeMillis();
        while (!_lambdaC.run()){
            if (System.currentTimeMillis()-time > timeToEnd){
                return;
            }
        }
        _lambdaA.run();
    }
}
