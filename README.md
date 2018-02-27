# T&V

is a tool that combines **Testing** and **Formal Verification** for **diagnosing** failures of model transformations verification.

It works by generation of counter examples for **ATL** model transformations.

Abstract
--------

In model-driven engineering (MDE), model transformation (MT) verification is essential for reliably producing software artefacts. Recent advancements have enabled automatic Hoare-style deductive verification for non-trivial MTs. Deductive verification result generally takes the format of either correct, failure or timeouts. The verification failures are usually hints for bugs in a MT, which take higher propriety for users to examine. However, without enough information, it is difficult for users to get insight of whether the failure complaint is genuine or spurious. To help users with this task, this paper presents a dynamic approach that determines inputs to reproduce failures, seamlessly integrates with the VeriATL (a verifier for the ATL transformation language) for diagnosing verification failure. 

Context
-------

This project is conducted by the [NaoMod](http://www.atlanmod.org) ([github](https://github.com/atlanmod)) team in Nantes.

It is the continuation of [VeriATL](https://github.com/veriatl) project.

Team
------

- Adel Ferdjoukh, Post-doc, University of Nantes, adel.ferdjoukh@univ-nantes.fr
- Zheng Cheng, Post-doc, INRIA, IMT Atlantique, Nantes, zheng.cheng@imt-atlantique.fr
- Jean-Marie Mottu, Associate Professor, University of Nantes, jean-marie.mottu@univ-nantes.fr
- Massimo Tisi, Associate Professor, University of Nantes, massimi.tisi@imt-atlantique.fr
 
 