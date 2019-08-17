/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jdbi.v3.core.mapper;

import java.util.Objects;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.jdbi.v3.core.config.ConfigRegistry;
import org.jdbi.v3.core.qualifier.QualifiedType;
import org.jdbi.v3.meta.Beta;

/**
 * Column mapper that handles any {@link Nonnull} {@link QualifiedType}.
 *
 * {@code @Nonnull} is stripped from the received qualified type,
 * the actual column mapper for the remaining qualified type is resolved,
 * and the mapped value is checked, throwing {@link NullPointerException} if null.
 *
 * This allows you to query for any column type and make {@code null} explicitly forbidden as a result,
 * for when declarative programming is your cup of tea.
 *
 * This factory should be registered by calling {@link ColumnMappers#addNonNullQualifier(Class)} as late as possible.
 */
@Beta
class NonnullColumnMapperFactory implements QualifiedColumnMapperFactory {
    @Override
    public Optional<ColumnMapper<?>> build(QualifiedType<?> type, ConfigRegistry config) {
        return config.get(ColumnMappers.class)
            .getNonNullQualifiers()
            .stream()
            // TODO wtf
            .map(nonNull -> type.hasQualifier(nonNull) ? type.remove(nonNull) : null)
            .filter(Objects::nonNull)
            .findFirst()
            .flatMap(config.get(ColumnMappers.class)::findFor)
            .map(mapper -> (r, i, ctx) -> Objects.requireNonNull(mapper.map(r, i, ctx), "type annotated with non-null qualifier got a null value"));
    }
}