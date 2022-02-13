package info.kgeorgiy.ja.aliev.student;

import info.kgeorgiy.java.advanced.student.GroupName;
import info.kgeorgiy.java.advanced.student.Student;
import info.kgeorgiy.java.advanced.student.StudentQuery;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StudentDB implements StudentQuery {


    private static <R> Stream<R> mapStream(List<Student> students,
                                           Function<Student, R> mapFunc) {
        return students
                .stream()
                .map(mapFunc);
    }

    private static <R> List<R> mapQuery(List<Student> students,
                                        Function<Student, R> mapFunc) {
        return mapStream(students, mapFunc)
                .collect(Collectors.toList());
    }

    private static final Comparator<Student> NAME_COMPARATOR =
            Comparator.comparing(Student::getLastName)
                    .thenComparing(Student::getFirstName)
                    .reversed()
                    .thenComparingInt(Student::getId);


    private String getExpandedName(Student student) {
        return String.join(" ", student.getFirstName(), student.getLastName());
    }

    private static <R> R query(Collection<Student> students,
                               Predicate<Student> predicate,
                               Comparator<Student> comparator,
                               Collector<Student, ?, R> collector) {
        return students
                .stream()
                .filter(predicate)
                .sorted(comparator)
                .collect(collector);
    }


    @Override
    public List<String> getFirstNames(List<Student> students) {
        return mapQuery(students, Student::getFirstName);
    }

    @Override
    public List<String> getLastNames(List<Student> students) {
        return mapQuery(students, Student::getLastName);
    }

    @Override
    public List<GroupName> getGroups(List<Student> students) {
        return mapQuery(students, Student::getGroup);
    }

    @Override
    public List<String> getFullNames(List<Student> students) {
        return mapQuery(students, this::getExpandedName);
    }

    @Override
    public Set<String> getDistinctFirstNames(List<Student> students) {
        return mapStream(students, Student::getFirstName)
                .sorted()
                .collect(Collectors.toCollection(ConcurrentSkipListSet::new));
    }

    @Override
    public String getMaxStudentFirstName(List<Student> students) {
        return students
                .stream()
                .max(Comparator.comparingInt(Student::getId))
                .map(Student::getFirstName)
                .orElse("");
    }

    @Override
    public List<Student> sortStudentsById(Collection<Student> students) {
        return query(students,
                student -> true,
                Comparator.comparingInt(Student::getId),
                Collectors.toList());
    }

    @Override
    public List<Student> sortStudentsByName(Collection<Student> students) {
        return query(students,
                student -> true,
                NAME_COMPARATOR,
                Collectors.toList());
    }

    @Override
    public List<Student> findStudentsByFirstName(Collection<Student> students, String name) {
        return query(students,
                student -> name.equals(student.getFirstName()),
                NAME_COMPARATOR,
                Collectors.toList());
    }

    @Override
    public List<Student> findStudentsByLastName(Collection<Student> students, String name) {
        return query(students,
                student -> name.equals(student.getLastName()),
                NAME_COMPARATOR,
                Collectors.toList());
    }

    @Override
    public List<Student> findStudentsByGroup(Collection<Student> students, GroupName group) {
        return query(students,
                student -> group.equals(student.getGroup()),
                NAME_COMPARATOR,
                Collectors.toList());
    }

    @Override
    public Map<String, String> findStudentNamesByGroup(Collection<Student> students, GroupName group) {
        return query(
                students,
                student -> group.equals(student.getGroup()),
                Comparator.naturalOrder(),
                Collectors.toMap(
                        Student::getLastName,
                        Student::getFirstName,
                        BinaryOperator.minBy(String::compareTo)
                )
        );
    }
}
