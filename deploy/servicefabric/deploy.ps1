Connect-ServiceFabricCluster localhost:19000

Write-Host 'Copying application package...'
Copy-ServiceFabricApplicationPackage -ApplicationPackagePath '.' -ImageStoreConnectionString 'file:C:\SfDevCluster\Data\ImageStoreShare' -ApplicationPackagePathInImageStore 'Store\MicroservicesPoC'

Write-Host 'Registering application type...'
Register-ServiceFabricApplicationType -ApplicationPathInImageStore 'Store\MicroservicesPoC'

New-ServiceFabricApplication -ApplicationName 'fabric:/MicroservicesPoC' -ApplicationTypeName 'MicroservicesPoCType' -ApplicationTypeVersion 1.0
