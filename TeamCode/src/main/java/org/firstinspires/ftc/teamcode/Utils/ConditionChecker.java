package org.firstinspires.ftc.teamcode.Utils;

public class ConditionChecker extends Thread{

    private LambdaBool _lambdaC;
    private Lambda _lambdaA;

    public ConditionChecker(LambdaBool lambdaCondition, Lambda lambdaAction){
        _lambdaC = lambdaCondition;
        _lambdaA = lambdaAction;
    }

    @Override
    public void run() {
        long time = System.currentTimeMillis();
        while (!_lambdaC.run()){
            if (System.currentTimeMillis()-time>2000){
                return;
            }
        }
        _lambdaA.run();
    }
}
