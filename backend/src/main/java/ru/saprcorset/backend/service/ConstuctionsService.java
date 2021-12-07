package ru.saprcorset.backend.service;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.saprcorset.backend.dto.CalculateResponseDTO;
import ru.saprcorset.backend.dto.ConstructionsDTO;
import ru.saprcorset.backend.resourse.ConstructionsResource;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
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
        return calc;
    }

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

    public CalculateResponseDTO calc(ConstructionsDTO constructionsDTO) {
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
        RealMatrix a = new Array2DRowRealMatrix(A);
        RealMatrix inverse = MatrixUtils.inverse(a);
        RealMatrix b = new Array2DRowRealMatrix(B);
        RealMatrix x = inverse.multiply(b);
        double[] delta = x.getColumn(0);
        List<Double> deltaList = new ArrayList<>();
        for (int i = 0; i <= rodsCount; i++) {
            deltaList.add(delta[i]);
        }
        CalculateResponseDTO calculateResponseDTO = new CalculateResponseDTO(1, deltaList, null, null, null, null, null, null, null, null);
        return calculateResponseDTO;
    }
}
