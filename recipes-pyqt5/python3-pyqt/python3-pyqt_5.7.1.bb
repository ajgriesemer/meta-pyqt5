SUMMARY = "Python Qt5 Bindings"
AUTHOR = "Andrew Griesemer @ dmcinfo.com"
HOMEPAGE = "github.com/ajgriesemer"
SECTION = "devel/python"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d32239bcb673463ab874e80d47fae504"

inherit qmake5 qmake5_paths python3-dir

DEPENDS = "sip sip-native qtbase python3"

SRC_URI = " \
    ${SOURCEFORGE_MIRROR}/project/pyqt/PyQt5/PyQt-${PV}/PyQt5_gpl-${PV}.tar.gz \
"

S = "${WORKDIR}/PyQt5_gpl-${PV}"
B = "${WORKDIR}/PyQt5_gpl-${PV}"

SRC_URI[md5sum] = "b3171b67c74aa63a3cd2f386660c898b"
SRC_URI[sha256sum] = "be849f212a074049b9ebc10b6c07dddefb86e6d30e8df8a5c715cbb2cf7fad14"

PYQT_MODULES = "QtQml QtGui QtWidgets QtCore"

DISABLED_FEATURES = "PyQt_Desktop_OpenGL PyQt_SessionManager"
DISABLED_FEATURES_append_arm = " PyQt_qreal_double"

do_configure() {
    echo "[Qt 5.7.1]" > pyqt.cfg
    echo "py_platform = linux" >> pyqt.cfg
    echo "py_inc_dir = %(sysroot)/${includedir}/${PYTHON_DIR}${PYTHON_ABI}" >> pyqt.cfg
    echo "py_pylib_dir = %(sysroot)/${libdir}/${PYTHON_DIR}${PYTHON_ABI}" >> pyqt.cfg
    echo "py_pylib_lib = ${PYTHON_DIR}mu" >> pyqt.cfg

    echo "pyqt_module_dir = ${D}/${PYTHON_SITEPACKAGES_DIR}" >> pyqt.cfg
    echo "pyqt_bin_dir = ${D}/${bindir}" >> pyqt.cfg
    echo "pyqt_sip_dir = ${D}/${datadir}/sip/PyQt5" >> pyqt.cfg
    echo "pyuic_interpreter = ${D}/${bindir}/python%(py_major).%(py_minor)" >> pyqt.cfg

    echo "pyqt_disabled_features = ${DISABLED_FEATURES}" >> pyqt.cfg
    echo "pyqt_modules = ${PYQT_MODULES}" >> pyqt.cfg

    python3 configure.py --no-stubs --confirm-license --verbose --static --qmake  ${STAGING_BINDIR_NATIVE}/qt5/qmake --configuration pyqt.cfg --sysroot ${STAGING_DIR_HOST} CC="${CC}" CXX="${CXX}" LINK="${CXX}" STRIP="" LINK_SHLIB="${CXX}" CFLAGS="${CFLAGS}" CXXFLAGS="${CXXFLAGS}" LFLAGS="${LDFLAGS}"
}
do_install() {
     oe_runmake install
}

FILES_${PN} += "${PYTHON_SITEPACKAGES_DIR}/PyQt5/*.py ${PYTHON_SITEPACKAGES_DIR}/PyQt5/uic ${datadir}/sip/PyQt5"
FILES_${PN}-staticdev += "${PYTHON_SITEPACKAGES_DIR}/PyQt5/*.a"
