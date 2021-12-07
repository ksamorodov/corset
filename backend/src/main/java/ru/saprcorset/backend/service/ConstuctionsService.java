package ru.saprcorset.backend.service;

import org.apache.commons.math3.linear.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.saprcorset.backend.dto.CalculateResponseDTO;
import ru.saprcorset.backend.dto.ConstructionsDTO;
import ru.saprcorset.backend.resourse.ConstructionsResource;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ConstuctionsService {
    private List<Double> L;
    private List<Double> F;
    private List<Double> Q;
    private List<Double> E;
    private List<Double> A;

    @Autowired
    private ConstructionsResource constructionsResource;

    @PostConstruct
    public void init() {

    }

    public Optional<Integer> save(ConstructionsDTO constructionsDTO) {
        return constructionsResource.save(constructionsDTO);
    }

    public Optional<ConstructionsDTO> getById(Integer id) {
        return constructionsResource.getById(id);
    }

    public List<ConstructionsDTO> getKernelsList() {
        return constructionsResource.getConstructionsList();
    }

    public void deleteByName(String name) {
        constructionsResource.deleteByName(name);
    }

    public CalculateResponseDTO calculate(Integer id) {
        CalculateResponseDTO calc = null;
        Optional<ConstructionsDTO> construction = getById(id);
        if (construction.isPresent()) {
            calc = calc(construction.get());
        }
        return null;
    }

    static int[] Node;

//    public static List<String> systemOfEquations (double[][] A, double[] b, int rodsCount, boolean leftLimit, boolean rightLimit ){
//        List<String> list = new ArrayList<>();
//        list.add("Система уравнений:");
//        for ( int i = 0; i <= rodsCount; i++ ){
//            for ( int j = 0; j <= rodsCount; j++ ){
//                if ( (( i==0 ) && ( j == 0 ))||(( i==rodsCount ) && ( j==rodsCount ))){
//                    break;
//                }
//                if( A[i][j] == 0 ) {continue;}
//                list.add( " + " + A[i][j] + "*EA/L * delta" + (j+1));
//            }
//            if (((i == 0)&&(leftLimit))||((i==rodsCount)&&(rightLimit))){continue;}
//            list.add( " = " + b[i] + "qL");
//        }
//        return list;
//    }

    public double[] bCalculate(List<ConstructionsDTO.KernelDTO> rods, int rodsCount, Object[] forces, boolean leftLimit, boolean rightLimit) {
        double[] b = new double[rodsCount + 1];
        for (int i = 0; i < F.size(); i++) {

            if (i == 0) {
                b[i] = F.get(0) + Q.get(0) * L.get(0) / 2;
                b[i + 1] = Q.get(0) * L.get(0) / 2;
            } else if (i == rodsCount) {
                b[i] = F.get(i);
                b[i] += Q.get(i - 1) * L.get(i - 1) / 2;
            } else {
                b[i] += F.get(i) + Q.get(i) * L.get(i) / 2;
                b[i + 1] = Q.get(i) * L.get(i) / 2;
            }
        }

        if (leftLimit == true) {
            b[0] = 0.0;
        }
        if (rightLimit == true) {
            b[rodsCount] = 0.0;
        }

        return b;
    }

    public double[][] ACalculate(List<ConstructionsDTO.KernelDTO> rods, int rodsCount, boolean leftLimit, boolean rightLimit) {
        double[][] AA = new double[rodsCount + 1][rodsCount + 1];

        for (int i = 0; i < rodsCount + 1; i++) {
            for (int j = 0; j < rodsCount; j++) {
                AA[i][j] = 0.0;
            }
        }

        for (int i = 0; i < rodsCount + 1; i++) {

            if (i == rodsCount) {
                AA[rodsCount][rodsCount] = E.get(rodsCount - 1) * A.get(rodsCount - 1) / L.get(rodsCount - 1);
            } else {
                Double k = (E.get(i) * A.get(i)) / L.get(i);
                AA[i][i] += k;
                AA[i + 1][i + 1] += k;
                AA[i + 1][i] -= k;
                AA[i][i + 1] -= k;
            }
        }


        if (leftLimit = true) {
            AA[0][0] = 1.0;
            AA[0][1] = 0.0;
            AA[1][0] = 0.0;
        }
        if (rightLimit == true) {
            AA[rodsCount][rodsCount] = 1.0;
            AA[rodsCount][rodsCount - 1] = 0.0;
            AA[rodsCount - 1][rodsCount] = 0.0;
        }
        return AA;
    }

    public static double N(ConstructionsDTO.KernelDTO rod, double x, double[] delta, int i) {
        return (rod.elasticModulus().doubleValue() * rod.kernelSize().doubleValue()) * (delta[i + 1] - delta[i]) / rod.kernelSize().doubleValue() + (rod.concentratedLoad().doubleValue() * rod.kernelSize().doubleValue()) / 2 * (1 - 2 * x / rod.kernelSize().doubleValue());
    }

    public static List<String> NFind(double[] delta, List<ConstructionsDTO.KernelDTO> rods, int rodsCount) {
        List<String> list = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        List<String> list3 = new ArrayList<>();
        List<String> list4 = new ArrayList<>();
        for (int i = 0; i < rodsCount; i++) {
            list.add("N" + (i + 1) + "(0) = " + N(rods.get(i), 0, delta, i));
            list2.add("N" + (i + 1) + "(" + rods.get(i).kernelSize() + ") = " + N(rods.get(i), rods.get(i).kernelSize().doubleValue(), delta, i));
            list3.add("sig" + (i + 1) + "(0) = " + N(rods.get(i), 0, delta, i) / rods.get(i).crossSectionalArea().doubleValue());
            list4.add("sig" + (i + 1) + "(" + rods.get(i).kernelSize() + ") = " + N(rods.get(i), rods.get(i).kernelSize().doubleValue(), delta, i) / rods.get(i).crossSectionalArea().doubleValue());

        }
        list2.forEach(e -> list.add(e));
        list3.forEach(e -> list.add(e));
        list4.forEach(e -> list.add(e));
        return list;

    }

    public static double U(ConstructionsDTO.KernelDTO rod, double x, double[] delta, int i) {
        return (delta[i] + (x / rod.kernelSize().doubleValue()) * (delta[i + 1] - delta[i]) + (rod.concentratedLoad().doubleValue() * rod.kernelSize().doubleValue() * rod.kernelSize().doubleValue() * x * (1 - x / rod.kernelSize().doubleValue())) / (2 * rod.elasticModulus().doubleValue() * rod.crossSectionalArea().doubleValue() * rod.kernelSize().doubleValue()));
    }

    public static List<String> UFind(double[] delta, List<ConstructionsDTO.KernelDTO> rods, int rodsCount) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < rodsCount; i++) {
            //  list.add("U"+ (i+1) +"(x) = " + delta[i] + " + ( x/" + rods.get(i).kernelSize().doubleValue()+"L"+")*( " +delta[i+1] + "-"+ delta[i] + ") + (" + rods.get(i).concentratedLoad().doubleValue()+"*"+ rods.get(i).kernelSize().doubleValue() + "^2*" + "x*(1 - x/" + rods.get(i).kernelSize().doubleValue() + "L))/(2*"+ rods.get(i).elasticModulus()+"E*" + rods.get(i).crossSectionalArea()+"A*"+ rods.get(i).kernelSize().doubleValue()+"L))");

            list.add("dU" + i + "/dx =  (" + delta[i + 1] + "-" + delta[i] + ")/" + rods.get(i).kernelSize().doubleValue() + "L  + (" + rods.get(i).concentratedLoad().doubleValue() + "*" + rods.get(i).kernelSize().doubleValue() + "L)/(2*" + rods.get(i).elasticModulus().doubleValue() + "E*" + rods.get(i).crossSectionalArea().doubleValue() + "A - (" + rods.get(i).concentratedLoad().doubleValue() + "q*x)/" + rods.get(i).elasticModulus().doubleValue() + "E*" + rods.get(i).crossSectionalArea().doubleValue() + "A = 0");

            Double x_extr = ((delta[i + 1] - delta[i]) / rods.get(i).kernelSize().doubleValue() + (rods.get(i).concentratedLoad().doubleValue() * rods.get(i).kernelSize().doubleValue()) / (2 * rods.get(i).elasticModulus().doubleValue() * rods.get(i).crossSectionalArea().doubleValue())) * (rods.get(i).elasticModulus().doubleValue() * rods.get(i).crossSectionalArea().doubleValue()) / rods.get(i).concentratedLoad().doubleValue();
            list.add("U" + (i + 1) + "(0) = " + U(rods.get(i), 0, delta, i));
            list.add("U" + (i + 1) + "(" + rods.get(i).kernelSize().doubleValue() + ") = " + U(rods.get(i), rods.get(i).kernelSize().doubleValue(), delta, i));
            if ((x_extr > 0) && (x_extr < rods.get(i).kernelSize().doubleValue()) && (x_extr != Double.POSITIVE_INFINITY) && (x_extr != Double.NEGATIVE_INFINITY)) {
                list.add("U" + i + "(" + x_extr + ") = " + U(rods.get(i), x_extr, delta, i));
            }
            list.add("x_extr" + x_extr.toString());
        }
        return list;
    }

    public CalculateResponseDTO calc(ConstructionsDTO constructionsDTO) {


        List<List<String>> res = new ArrayList<>();
        L = new ArrayList<>();
        constructionsDTO.kernels().forEach(i -> {
            if (i.kernelSize() != null)
                L.add(i.kernelSize().doubleValue());
        });

        F = new ArrayList<>();
        constructionsDTO.kernels().forEach(i -> {
            F.add(i.concentratedLoad().doubleValue());
        });

        Q = new ArrayList<>();
        constructionsDTO.kernels().forEach(i -> {
            if (i.kernelSize() != null)
                Q.add(i.linearVoltage().doubleValue());
        });

        E = new ArrayList<>();
        constructionsDTO.kernels().forEach(i -> {
            if (i.kernelSize() != null)
                E.add(i.elasticModulus().doubleValue());
        });

        A = new ArrayList<>();
        constructionsDTO.kernels().forEach(i -> {
            if (i.kernelSize() != null)
                A.add(i.crossSectionalArea().doubleValue());
        });

        List<String> list = new ArrayList<>();
        boolean leftLimit = constructionsDTO.leftSupport();
        boolean rightLimit = constructionsDTO.rightSupport();

        List<ConstructionsDTO.KernelDTO> listOfRods = constructionsDTO.kernels();
        List<Double> conLoad = new ArrayList<>();
        constructionsDTO.kernels().forEach(i -> conLoad.add(i.concentratedLoad().doubleValue()));

        List<Double> fors = new ArrayList();
        constructionsDTO.kernels().forEach(e -> fors.add(e.concentratedLoad() != null ? e.concentratedLoad().doubleValue() : 0));
        Object[] listOfForces = fors.toArray();

        int rodsCount = Q.size();

        double[] B = bCalculate(listOfRods, rodsCount, listOfForces, leftLimit, rightLimit);
        double A[][] = ACalculate(listOfRods, rodsCount, leftLimit, rightLimit);

        // List<String> stringList2 = systemOfEquations(A, B, rodsCount, leftLimit, rightLimit);
        RealMatrix a = new Array2DRowRealMatrix(A);
        RealMatrix inverse = MatrixUtils.inverse(a);
        //       RealMatrix newA = new LUDecomposition(a).getSolver().getInverse();
        RealMatrix b = new Array2DRowRealMatrix(B);
        //System.out.println("Input a: " + a);
        //System.out.println("Inverse b: " + b);

        RealMatrix x = inverse.multiply(b);

        //  list.add("Inverse x: " + x);
        double[] delta = x.getColumn(0);
        List<Double> deltaList = new ArrayList<>();
        for (int i = 0; i <= rodsCount; i++) {
            deltaList.add(delta[i]);
        }
        CalculateResponseDTO calculateResponseDTO = new CalculateResponseDTO(1, deltaList, null, null, null, null, null, null, null, null);

        List<String> stringList1 = NFind(delta, listOfRods, rodsCount);
        List<String> stringList = UFind(delta, listOfRods, rodsCount);

        res.add(list);
        // res.add(stringList2);
        res.add(stringList1);
        res.add(stringList);
        return calculateResponseDTO;
    }
}
