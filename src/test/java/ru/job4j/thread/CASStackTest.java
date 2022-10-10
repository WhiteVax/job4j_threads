package ru.job4j.thread;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class CASStackTest {

    @Test
    public void when3PushThen3Poll() {
        CASStack<Integer> stack = new CASStack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertThat(stack.poll()).isEqualTo(3);
        assertThat(stack.poll()).isEqualTo(2);
        assertThat(stack.poll()).isEqualTo(1);
    }

    @Test
    public void when1PushThen1Poll() {
        CASStack<Integer> stack = new CASStack<>();
        stack.push(1);
        assertThat(stack.poll()).isEqualTo(1);
    }
}