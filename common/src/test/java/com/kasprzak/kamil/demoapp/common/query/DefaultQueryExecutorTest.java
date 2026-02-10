package com.kasprzak.kamil.demoapp.common.query;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultQueryExecutorTest {

        @Mock
        private QueryHandler<Query, QueryResult> mockHandler;

        private DefaultQueryExecutor executor;

        @Test
        void shouldReturnResult() throws Exception {
            executor = new DefaultQueryExecutor(List.of(mockHandler));
            Query query = mock(Query.class);
            QueryResult expectedResult = mock(QueryResult.class);

            when(mockHandler.supports(query)).thenReturn(true);
            when(mockHandler.handle(query)).thenReturn(expectedResult);

            QueryResult result = executor.execute(query, QueryResult.class);

            assertSame(expectedResult, result);
            verify(mockHandler, times(1)).handle(query);
        }

        @Test
        void shouldThrowQueryHandlerNotFoundException() {
            executor = new DefaultQueryExecutor(List.of(mockHandler));
            Query query = mock(Query.class);

            when(mockHandler.supports(query)).thenReturn(false);

            assertThrows(QueryHandlerNotFoundException.class,
                    () -> executor.execute(query, QueryResult.class));
        }

        @Test
        void shouldThrowClassCastException() throws Exception {
            executor = new DefaultQueryExecutor(List.of(mockHandler));
            Query query = mock(Query.class);
            QueryResult result = mock(QueryResult.class);

            when(mockHandler.supports(query)).thenReturn(true);
            when(mockHandler.handle(query)).thenReturn(result);


            assertThrows(ClassCastException.class,
                    () -> executor.execute(query, AnotherQueryResult.class));
        }

        static class AnotherQueryResult implements QueryResult {}
    }
