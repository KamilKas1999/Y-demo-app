package com.kasprzak.kamil.demoapp.common.query;

import com.kasprzak.kamil.demoapp.common.exceptions.BusinesException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class DefaultQueryExecutor implements QueryExecutor {

    private List<QueryHandler<?, ?>> commandHandlers;

    @Override
    public <T extends QueryResult> T execute(Query query, Class<T> resultType) throws QueryHandlerNotFoundException, BusinesException {
        var handler = commandHandlers.stream()
                .filter(h -> h.supports(query))
                .map(h -> (QueryHandler<Query, QueryResult>) h)
                .findAny()
                .orElseThrow(() -> new QueryHandlerNotFoundException(query.getClass().getSimpleName()));

        var result = handler.handle(query);

        if (!resultType.isInstance(result)) {
            throw new ClassCastException("Expected result type: " + resultType + ", but got: " + result.getClass());
        }

        return resultType.cast(result);
    }
}
