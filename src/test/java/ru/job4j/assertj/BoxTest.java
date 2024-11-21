package ru.job4j.assertj;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withPrecision;

class BoxTest {

    @Test
    void isThisSphere() {
        Box box = new Box(0, 10);
        String name = box.whatsThis();
        assertThat(name).isEqualTo("Sphere");
    }

    @Test
    void isThisTetrahedron() {
        Box box = new Box(4, 8);
        String name = box.whatsThis();
        assertThat(name).isEqualTo("Tetrahedron");
    }

    @Test
    void isThisCube() {
        Box box = new Box(8, 12);
        String name = box.whatsThis();
        assertThat(name).isEqualTo("Cube");
    }

    @Test
    void getNumberOfVerticesForSphere() {
        Box box = new Box(0, 10);
        int vertex = box.getNumberOfVertices();
        assertThat(vertex).isEqualTo(0);
    }

    @Test
    void getNumberOfVerticesForTetrahedron() {
        Box box = new Box(4, 8);
        int vertex = box.getNumberOfVertices();
        assertThat(vertex).isEqualTo(4);
    }

    @Test
    void getNumberOfVerticesForCube() {
        Box box = new Box(8, 12);
        int vertex = box.getNumberOfVertices();
        assertThat(vertex).isEqualTo(8);
    }

    @Test
    void isExistTrue() {
        Box box = new Box(8, 12);
        boolean result = box.isExist();
        assertThat(result).isTrue();
    }

    @Test
    void isExistFalse() {
        Box box = new Box(-1, -1);
        boolean result = box.isExist();
        assertThat(result).isFalse();
    }

    @Test
    void getAreaSphere() {
        Box box = new Box(0, 10);
        double result = box.getArea();
        assertThat(result).isEqualTo(1256.637d, withPrecision(0.001d));
    }

    @Test
    void getAreaTetrahedron() {
        Box box = new Box(4, 8);
        double result = box.getArea();
        assertThat(result).isEqualTo(110.851, withPrecision(0.001d));
    }

    @Test
    void getAreaCube() {
        Box box = new Box(8, 12);
        double result = box.getArea();
        assertThat(result).isEqualTo(864.0d);
    }
}