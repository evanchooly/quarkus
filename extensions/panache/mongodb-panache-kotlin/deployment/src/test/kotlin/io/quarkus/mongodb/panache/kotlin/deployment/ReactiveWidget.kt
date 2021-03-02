package io.quarkus.mongodb.panache.kotlin.deployment

import io.quarkus.mongodb.panache.kotlin.reactive.ReactivePanacheMongoCompanion
import io.quarkus.mongodb.panache.kotlin.reactive.ReactivePanacheMongoEntity
import io.quarkus.mongodb.panache.kotlin.reactive.ReactivePanacheQuery
import io.quarkus.mongodb.panache.kotlin.reactive.runtime.KotlinReactiveMongoOperations.Companion.INSTANCE
import io.quarkus.mongodb.panache.reactive.ReactivePanacheUpdate
import io.quarkus.mongodb.reactive.ReactiveMongoCollection
import io.quarkus.mongodb.reactive.ReactiveMongoDatabase
import io.quarkus.panache.common.Parameters
import io.quarkus.panache.common.Sort
import io.smallrye.mutiny.Multi
import io.smallrye.mutiny.Uni
import org.bson.Document
import org.bson.types.ObjectId

@Suppress("UNCHECKED_CAST", "unused")
class ReactiveWidget : ReactivePanacheMongoEntity() {
    companion object : ReactivePanacheMongoCompanion<ReactiveWidget> {
        fun target_count(): Uni<Long> =
            INSTANCE.count(ReactiveWidget::class.java)

        fun target_count(query: String, vararg params: Any?): Uni<Long> =
            INSTANCE.count(ReactiveWidget::class.java, query, *params)

        fun target_count(query: String, params: Map<String, Any?>): Uni<Long> =
            INSTANCE.count(ReactiveWidget::class.java, query, params)

        fun target_count(query: String, params: Parameters): Uni<Long> =
            INSTANCE.count(ReactiveWidget::class.java, query, params)

        fun target_count(query: Document): Uni<Long> =
            INSTANCE.count(ReactiveWidget::class.java, query)

        fun target_deleteAll(): Uni<Long> =
            INSTANCE.deleteAll(ReactiveWidget::class.java)

        fun target_deleteById(id: ObjectId): Uni<Boolean> =
            INSTANCE.deleteById(ReactiveWidget::class.java, id)

        fun target_delete(query: String, vararg params: Any?): Uni<Long> =
            INSTANCE.delete(ReactiveWidget::class.java, query, *params)

        fun target_delete(query: String, params: Map<String, Any?>): Uni<Long> =
            INSTANCE.delete(ReactiveWidget::class.java, query, params)

        fun target_delete(query: String, params: Parameters): Uni<Long> =
            INSTANCE.delete(ReactiveWidget::class.java, query, params)

        fun target_delete(query: Document): Uni<Long> =
            INSTANCE.delete(ReactiveWidget::class.java, query)

        fun target_findById(id: ObjectId): Uni<ReactiveWidget?> =
            INSTANCE.findById(ReactiveWidget::class.java,id) as Uni<ReactiveWidget?>
         
        fun target_find(query: String, vararg params: Any?): ReactivePanacheQuery<ReactiveWidget> =
            INSTANCE.find(ReactiveWidget::class.java,query, *params) as ReactivePanacheQuery<ReactiveWidget>
         
        fun target_find(query: String, sort: Sort, vararg params: Any?): ReactivePanacheQuery<ReactiveWidget> =
            INSTANCE.find(ReactiveWidget::class.java,query, sort, *params) as ReactivePanacheQuery<ReactiveWidget>
         
        fun target_find(query: String, params: Map<String, Any?>): ReactivePanacheQuery<ReactiveWidget> =
            INSTANCE.find(ReactiveWidget::class.java,query, params) as ReactivePanacheQuery<ReactiveWidget>
         
        fun target_find(query: String, sort: Sort, params: Map<String, Any?>): ReactivePanacheQuery<ReactiveWidget> =
            INSTANCE.find(ReactiveWidget::class.java,query, sort, params) as ReactivePanacheQuery<ReactiveWidget>
         
        fun target_find(query: String, params: Parameters): ReactivePanacheQuery<ReactiveWidget> =
            INSTANCE.find(ReactiveWidget::class.java,query, params) as ReactivePanacheQuery<ReactiveWidget>
         
        fun target_find(query: String, sort: Sort, params: Parameters): ReactivePanacheQuery<ReactiveWidget> =
            INSTANCE.find(ReactiveWidget::class.java,query, sort, params) as ReactivePanacheQuery<ReactiveWidget>
         
        fun target_find(query: Document): ReactivePanacheQuery<ReactiveWidget> =
            INSTANCE.find(ReactiveWidget::class.java,query) as ReactivePanacheQuery<ReactiveWidget>
         
        fun target_find(query: Document, sort: Document): ReactivePanacheQuery<ReactiveWidget> =
            INSTANCE.find(ReactiveWidget::class.java,query, sort) as ReactivePanacheQuery<ReactiveWidget>
         
        fun target_findAll(): ReactivePanacheQuery<ReactiveWidget> =
            INSTANCE.findAll(ReactiveWidget::class.java) as ReactivePanacheQuery<ReactiveWidget>
         
        fun target_findAll(sort: Sort): ReactivePanacheQuery<ReactiveWidget> =
            INSTANCE.findAll(ReactiveWidget::class.java,sort) as ReactivePanacheQuery<ReactiveWidget>
         
        fun target_list(query: String, vararg params: Any?): Uni<List<ReactiveWidget>> =
            INSTANCE.list(ReactiveWidget::class.java,query, *params) as Uni<List<ReactiveWidget>>
         
        fun target_list(query: String, sort: Sort, vararg params: Any?): Uni<List<ReactiveWidget>> =
            INSTANCE.list(ReactiveWidget::class.java,query, sort, *params) as Uni<List<ReactiveWidget>>
         
        fun target_list(query: String, params: Map<String, Any?>): Uni<List<ReactiveWidget>> =
            INSTANCE.list(ReactiveWidget::class.java,query, params) as Uni<List<ReactiveWidget>>
         
        fun target_list(query: String, sort: Sort, params: Map<String, Any?>): Uni<List<ReactiveWidget>> =
            INSTANCE.list(ReactiveWidget::class.java,query, sort, params) as Uni<List<ReactiveWidget>>
         
        fun target_list(query: String, params: Parameters): Uni<List<ReactiveWidget>> =
            INSTANCE.list(ReactiveWidget::class.java,query, params) as Uni<List<ReactiveWidget>>
         
        fun target_list(query: String, sort: Sort, params: Parameters): Uni<List<ReactiveWidget>> =
            INSTANCE.list(ReactiveWidget::class.java,query, sort, params) as Uni<List<ReactiveWidget>>
         
        fun target_list(query: Document): Uni<List<ReactiveWidget>> =
            INSTANCE.list(ReactiveWidget::class.java,query) as Uni<List<ReactiveWidget>>
         
        fun target_list(query: Document, sort: Document): Uni<List<ReactiveWidget>> =
            INSTANCE.list(ReactiveWidget::class.java,query, sort) as Uni<List<ReactiveWidget>>
         
        fun target_listAll(): Uni<List<ReactiveWidget>> =
            INSTANCE.listAll(ReactiveWidget::class.java) as Uni<List<ReactiveWidget>>
         
        fun target_listAll(sort: Sort): Uni<List<ReactiveWidget>> =
            INSTANCE.listAll(ReactiveWidget::class.java,sort) as Uni<List<ReactiveWidget>>
         
        fun target_mongoCollection(): ReactiveMongoCollection<ReactiveWidget> =
            INSTANCE.mongoCollection(ReactiveWidget::class.java) as ReactiveMongoCollection<ReactiveWidget>
         
        fun target_mongoDatabase(): ReactiveMongoDatabase =
            INSTANCE.mongoDatabase(ReactiveWidget::class.java)
         
        fun target_stream(query: String, vararg params: Any?): Multi<ReactiveWidget> =
            INSTANCE.stream(ReactiveWidget::class.java,query, *params) as Multi<ReactiveWidget>
         
        fun target_stream(query: String, sort: Sort, vararg params: Any?): Multi<ReactiveWidget> =
            INSTANCE.stream(ReactiveWidget::class.java,query, sort, *params) as Multi<ReactiveWidget>
         
        fun target_stream(query: String, params: Map<String, Any?>): Multi<ReactiveWidget> =
            INSTANCE.stream(ReactiveWidget::class.java,query, params) as Multi<ReactiveWidget>
         
        fun target_stream(query: String, sort: Sort, params: Map<String, Any?>): Multi<ReactiveWidget> =
            INSTANCE.stream(ReactiveWidget::class.java,query, sort, params) as Multi<ReactiveWidget>
         
        fun target_stream(query: String, params: Parameters): Multi<ReactiveWidget> =
            INSTANCE.stream(ReactiveWidget::class.java,query, params) as Multi<ReactiveWidget>
         
        fun target_stream(query: String, sort: Sort, params: Parameters): Multi<ReactiveWidget> =
            INSTANCE.stream(ReactiveWidget::class.java,query, sort, params) as Multi<ReactiveWidget>
         
        fun target_stream(query: Document): Multi<ReactiveWidget> =
            INSTANCE.stream(ReactiveWidget::class.java,query) as Multi<ReactiveWidget>
         
        fun target_stream(query: Document, sort: Document): Multi<ReactiveWidget> =
            INSTANCE.stream(ReactiveWidget::class.java,query, sort) as Multi<ReactiveWidget>
         
        fun target_streamAll(): Multi<ReactiveWidget> =
            INSTANCE.streamAll(ReactiveWidget::class.java) as Multi<ReactiveWidget>
         
        fun target_streamAll(sort: Sort): Multi<ReactiveWidget> =
            INSTANCE.streamAll(ReactiveWidget::class.java,sort) as Multi<ReactiveWidget>
         
        fun target_update(update: String, vararg params: Any?): ReactivePanacheUpdate =
            INSTANCE.update(ReactiveWidget::class.java,update, *params)
         
        fun target_update(update: String, params: Map<String, Any?>): ReactivePanacheUpdate =
            INSTANCE.update(ReactiveWidget::class.java,update, params)
         
        fun target_update(update: String, params: Parameters): ReactivePanacheUpdate =
            INSTANCE.update(ReactiveWidget::class.java,update, params)
         
    }
}
