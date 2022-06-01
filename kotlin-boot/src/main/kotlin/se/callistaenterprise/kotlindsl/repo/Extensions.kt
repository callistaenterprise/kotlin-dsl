package se.callistaenterprise.kotlindsl.repo

import org.jooq.Field
import org.jooq.Function1
import org.jooq.Function10
import org.jooq.Function11
import org.jooq.Function12
import org.jooq.Function13
import org.jooq.Function14
import org.jooq.Function15
import org.jooq.Function16
import org.jooq.Function17
import org.jooq.Function18
import org.jooq.Function19
import org.jooq.Function2
import org.jooq.Function20
import org.jooq.Function21
import org.jooq.Function22
import org.jooq.Function3
import org.jooq.Function4
import org.jooq.Function5
import org.jooq.Function6
import org.jooq.Function7
import org.jooq.Function8
import org.jooq.Function9
import org.jooq.Record1
import org.jooq.Record10
import org.jooq.Record11
import org.jooq.Record12
import org.jooq.Record13
import org.jooq.Record14
import org.jooq.Record15
import org.jooq.Record16
import org.jooq.Record17
import org.jooq.Record18
import org.jooq.Record19
import org.jooq.Record2
import org.jooq.Record20
import org.jooq.Record21
import org.jooq.Record22
import org.jooq.Record3
import org.jooq.Record4
import org.jooq.Record5
import org.jooq.Record6
import org.jooq.Record7
import org.jooq.Record8
import org.jooq.Record9
import org.jooq.Records
import org.jooq.Result

/** These extension functions were taken directly from the jOOQ source code on Github, and will be included in the next minor release of jOOQ (3.17) */

fun <T1, E> Field<Result<Record1<T1>>>.mapping(f: Function1<T1, E>): Field<List<E>> = convertFrom { it.map(Records.mapping(f)) }

fun <T1, T2, E> Field<Result<Record2<T1, T2>>>.mapping(f: Function2<T1, T2, E>): Field<List<E>> = convertFrom { it.map(Records.mapping(f)) }

fun <T1, T2, T3, E> Field<Result<Record3<T1, T2, T3>>>.mapping(f: Function3<T1, T2, T3, E>): Field<List<E>> = convertFrom { it.map(Records.mapping(f)) }

fun <T1, T2, T3, T4, E> Field<Result<Record4<T1, T2, T3, T4>>>.mapping(f: Function4<T1, T2, T3, T4, E>): Field<List<E>> =
    convertFrom { it.map(Records.mapping(f)) }

fun <T1, T2, T3, T4, T5, E> Field<Result<Record5<T1, T2, T3, T4, T5>>>.mapping(f: Function5<T1, T2, T3, T4, T5, E>): Field<List<E>> =
    convertFrom { it.map(Records.mapping(f)) }

fun <T1, T2, T3, T4, T5, T6, E> Field<Result<Record6<T1, T2, T3, T4, T5, T6>>>.mapping(f: Function6<T1, T2, T3, T4, T5, T6, E>): Field<List<E>> =
    convertFrom { it.map(Records.mapping(f)) }

fun <T1, T2, T3, T4, T5, T6, T7, E> Field<Result<Record7<T1, T2, T3, T4, T5, T6, T7>>>.mapping(f: Function7<T1, T2, T3, T4, T5, T6, T7, E>): Field<List<E>> =
    convertFrom { it.map(Records.mapping(f)) }

fun <T1, T2, T3, T4, T5, T6, T7, T8, E> Field<Result<Record8<T1, T2, T3, T4, T5, T6, T7, T8>>>.mapping(f: Function8<T1, T2, T3, T4, T5, T6, T7, T8, E>): Field<List<E>> =
    convertFrom { it.map(Records.mapping(f)) }

fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, E> Field<Result<Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>>>.mapping(f: Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, E>): Field<List<E>> =
    convertFrom { it.map(Records.mapping(f)) }

fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, E> Field<Result<Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>>>.mapping(f: Function10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, E>): Field<List<E>> =
    convertFrom { it.map(Records.mapping(f)) }

fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, E> Field<Result<Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>>>.mapping(f: Function11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, E>): Field<List<E>> =
    convertFrom { it.map(Records.mapping(f)) }

fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, E> Field<Result<Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>>>.mapping(f: Function12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, E>): Field<List<E>> =
    convertFrom { it.map(Records.mapping(f)) }

fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, E> Field<Result<Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>>>.mapping(f: Function13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, E>): Field<List<E>> =
    convertFrom { it.map(Records.mapping(f)) }

fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, E> Field<Result<Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>>>.mapping(
    f: Function14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, E>
): Field<List<E>> = convertFrom { it.map(Records.mapping(f)) }

fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, E> Field<Result<Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>>>.mapping(
    f: Function15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, E>
): Field<List<E>> = convertFrom { it.map(Records.mapping(f)) }

fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, E> Field<Result<Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>>>.mapping(
    f: Function16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, E>
): Field<List<E>> = convertFrom { it.map(Records.mapping(f)) }

fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, E> Field<Result<Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>>>.mapping(
    f: Function17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, E>
): Field<List<E>> = convertFrom { it.map(Records.mapping(f)) }

fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, E> Field<Result<Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>>>.mapping(
    f: Function18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, E>
): Field<List<E>> = convertFrom { it.map(Records.mapping(f)) }

fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, E> Field<Result<Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>>>.mapping(
    f: Function19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, E>
): Field<List<E>> = convertFrom { it.map(Records.mapping(f)) }

fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, E> Field<Result<Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>>>.mapping(
    f: Function20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, E>
): Field<List<E>> = convertFrom { it.map(Records.mapping(f)) }

fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, E> Field<Result<Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>>>.mapping(
    f: Function21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, E>
): Field<List<E>> = convertFrom { it.map(Records.mapping(f)) }

fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, E> Field<Result<Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>>>.mapping(
    f: Function22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, E>
): Field<List<E>> = convertFrom { it.map(Records.mapping(f)) }
