Connect-ServiceFabricCluster movies-servicefabric.eastus.cloudapp.azure.com:19000 -TimeoutSec 1200

Write-Host 'Copying application packages...'
Copy-ServiceFabricApplicationPackage -ApplicationPackagePath '.\Discovery' -ImageStoreConnectionString "fabric:ImageStore"
Copy-ServiceFabricApplicationPackage -ApplicationPackagePath '.\Config' -ImageStoreConnectionString "fabric:ImageStore"
Copy-ServiceFabricApplicationPackage -ApplicationPackagePath '.\Movie' -ImageStoreConnectionString "fabric:ImageStore"
Copy-ServiceFabricApplicationPackage -ApplicationPackagePath '.\UI' -ImageStoreConnectionString "fabric:ImageStore"
Copy-ServiceFabricApplicationPackage -ApplicationPackagePath '.\User' -ImageStoreConnectionString "fabric:ImageStore"
Copy-ServiceFabricApplicationPackage -ApplicationPackagePath '.\Rating' -ImageStoreConnectionString "fabric:ImageStore"




Write-Host 'Registering application types...'
Register-ServiceFabricApplicationType -ApplicationPathInImageStore Discovery
Register-ServiceFabricApplicationType -ApplicationPathInImageStore Config
Register-ServiceFabricApplicationType -ApplicationPathInImageStore Movie
Register-ServiceFabricApplicationType -ApplicationPathInImageStore UI
Register-ServiceFabricApplicationType -ApplicationPathInImageStore User
Register-ServiceFabricApplicationType -ApplicationPathInImageStore Rating


New-ServiceFabricApplication -ApplicationName 'fabric:/Discovery' -ApplicationTypeName 'Discovery' -ApplicationTypeVersion 1.0
New-ServiceFabricApplication -ApplicationName 'fabric:/Config' -ApplicationTypeName 'Config' -ApplicationTypeVersion 1.0
New-ServiceFabricApplication -ApplicationName 'fabric:/Movie' -ApplicationTypeName 'Movie' -ApplicationTypeVersion 1.0
New-ServiceFabricApplication -ApplicationName 'fabric:/User' -ApplicationTypeName 'User' -ApplicationTypeVersion 1.0
New-ServiceFabricApplication -ApplicationName 'fabric:/UI' -ApplicationTypeName 'UI' -ApplicationTypeVersion 1.0
New-ServiceFabricApplication -ApplicationName 'fabric:/Rating' -ApplicationTypeName 'Rating' -ApplicationTypeVersion 1.0





