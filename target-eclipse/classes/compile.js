var compile = function(source, paths) {

    globalPaths = paths;

    var parser = new(less.Parser)({
        filename: source
    });

    parser.parse(readFile(source), function (e, tree) {

        if (tree)
            result = tree.toCSS({ compress: true });

        if (e instanceof Object) {
            console.log('There is '+e.type+' Error '+e.message+' on line '+e.line+' in column '+e.column);
            throw e;
        }

    });

    return result;
}
